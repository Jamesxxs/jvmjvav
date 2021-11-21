Goal:  
Implement a subset of the Java language, including a Parser front end and interpter back end.  The language is Java, and the goal is to achieve as many features as possible.  The back end does not involve JIT, PGO optimization, and is merely an interpreter (using Java to interpret class files).  
The general arrangement is as follows:  
1. Implement the search for class files  
2. Implement class file parsing  
3. Implement the runtime data area  
4. Implement a simple interpreter  
5. Extend the interpreter to realize advanced functions such as method call, object orientation and exception handling  
6. Implement parser  

Possible problems:  
Class file parsing  
How do you implement classes and objects in a virtual machine  
How to implement exception handling  

Development environment:
Windows10，IDEA

## Class file search
The main file is in the classpath directory, and the command line parameters are parsed using 'jCommander'.  
Documentation for 'jCommander' : [https://jcommander.org/](https://jcommander.org/)  
The Java classpath is divided into three parts: the startup classpath, the extension classpath, and the user classpath  
The classpath is specified by the user using command line arguments  
The order of execution is classpath initialization -> find user-supplied classes  
The Entry interface is used to represent the classpath entries, which combine the four classes DirEntry, ZipEntry, CompositeEntry, and WildcardEntry. DirEntry represents the directory classpath, and ZipEntry represents the ZIP or JAR classpath.  A CompositeEntry is used to represent paths for file separators to split multiple files, and WildcardEntry is used to represent cases where files in the directory end with '*'.  
## Class file parsing

The basic unit of data that makes up a class file is the byte in which the data is stored in a big-endian fashion.  
The key is the ClassReader class, which AIDS in byte manipulation.
```java
public class ClassReader {
    private final ByteBuffer buf;
    ClassReader(byte[] data){
        buf = ByteBuffer.allocate(data.length+5);
        buf.put(data);
        buf.rewind();
    }
    
    public byte readUint8() {
        return buf.get();
    }
    
    public char readUint16() {
        byte[] tmp = new byte[2];
        buf.get(tmp,0,2);
        return (char) (((tmp[0] & 0xFF) << 8) | (tmp[1] & 0xFF));
    }
    
    public int readUint32()  {
        byte[] tmp = new byte[4];
        buf.get(tmp,0,4);
        return  ((tmp[3]&0xff) |((tmp[2]&0xff) << 8) | ((tmp[1]&0xff)  << 16) | ((tmp[0]&0xff) << 24));
    }
    
    public long readUint64() {
        byte[] tmp = new byte[8];
        buf.get(tmp,0,8);
        return  (((long)(tmp[0] & 0xFF) << 56) | ((long)(tmp[1] & 0xFF) << 48) | ((long)(tmp[2] & 0xFF) << 40)
                | ((long)(tmp[3] & 0xFF) << 32) |
                (tmp[4] & 0xFF << 24) | (tmp[5] & 0xFF << 16) | (tmp[6] & 0xFF << 8) | (tmp[7] & 0xFF));
    }
    public char[] readUint16s() {
        var n = readUint16();
        char[] s = new char[n];
        for(int i=0;i<n;i++){
            s[i] = readUint16();
        }
        return s;
    }

    public byte[] readBytes(int n) {
        byte[] ret = new byte[n];
        buf.get(ret, 0, n);
        return ret;
    }
}
```
The byte stream is read in the following order:
```java
    void read(ClassReader reader) throws Exception {
        readAndCheckMagic(reader);
        readAndCheckVersion(reader);
        constantPool = new ConstantPool().readConstantPool(reader);
        accessFlags = reader.readUint16();
        thisClass = reader.readUint16();
        superClass = reader.readUint16();
        interfaces = reader.readUint16s();
        fields = MemberInfo.readMembers(reader, constantPool);
        methods = MemberInfo.readMembers(reader, constantPool);
        attributes = AttributeInfo.readAttributes(reader, constantPool);
    }
```
The magic number must be `0xCAFEBABE` class, superclass, and interface table are all stored as constant pool indexes.  
Fields, methods, and classes all have access flags that use `bitmask`, followed by a constant pool index that gives a field or method descriptor, and finally a property list.  
The constant pool contains a lot of constant information, including numeric and string constants, class and interface names, field and method names, and so on.  To indicate a constant type as an 8-bit unsigned integer:  
```java
int CONSTANT_CLASS = 7;
    int CONSTANT_FIELDREF = 9;
    int CONSTANT_METHODREF = 10;
    int CONSTANT_INTERFACE_METHODREF = 11;
    int CONSTANT_STRING = 8;
    int CONSTANT_INTEGER = 3;
    int CONSTANT_FLOAT = 4;
    int CONSTANT_LONG = 5;
    int CONSTANT_DOUBLE = 6;
    int CONSTANT_NAME_AND_TYPE = 12;
    int CONSTANT_UTF8 = 1;
    int CONSTANT_METHOD_HANDLE = 15;
    int CONSTANT_METHOD_TYPE = 16;
    int CONSTANT_INVOKE_DYNAMIC = 18;

    /**
     * Read constant information
     *
     * @param reader
     */
    void readInfo(ClassReader reader) throws IOException;

    /**
     * Read the tag value, new creates the concrete constant, and then call readInfo to read the constant information  
     * @param reader
     * @param cp
     * @return
     * @throws Exception
     */
    static ConstantInfo readConstantInfo(ClassReader reader, ConstantPool cp) throws Exception {
        var tag = reader.readUint8();
        ConstantInfo ret = switch (tag) {
            case CONSTANT_INTEGER -> new ConstantIntegerInfo();
            case CONSTANT_FLOAT -> new ConstantFloatInfo();
            case CONSTANT_LONG -> new ConstantLongInfo();
            case CONSTANT_DOUBLE -> new ConstantDoubleInfo();
            case CONSTANT_UTF8 -> new ConstantUtf8Info();
            case CONSTANT_STRING -> new ConstantStringInfo(cp);
            case CONSTANT_CLASS -> new ConstantClassInfo(cp);
            case CONSTANT_FIELDREF -> new ConstantFieldRefInfo(cp);
            case CONSTANT_METHODREF -> new ConstantMethodRefInfo(cp);
            case CONSTANT_INTERFACE_METHODREF -> new ConstantInterfaceMethodRefInfo(cp);
            case CONSTANT_NAME_AND_TYPE -> new ConstantNameAndTypeInfo();
// Support SE7 Invokedynamic  
// that is, the method referenced by the call point qualifier is dynamically resolved at run time and then executed  
            case CONSTANT_METHOD_TYPE -> new ConstantMethodTypeInfo();
            case CONSTANT_METHOD_HANDLE -> new ConstantMethodHandleInfo();
            case CONSTANT_INVOKE_DYNAMIC -> new ConstantInvokeDynamicInfo();
            default -> throw new Exception("java.lang.ClassFormatError: constant pool tag!");
        };
        ret.readInfo(reader);
        return ret;
    }
```
The bytecode of a method is stored in a property table, marked by Deprecated and Synthetic, SourceFile indicating the SourceFile name, ConstantValue indicating the value of a constant expression, ConstantValue indicating the value of a constant expression,  The Code attribute stores method information such as bytecode, Exceptions represent the table of thrown Exceptions, LineNumberTable and LocalVariableTable store method line numbers and local variable information.  


## Implement the runtime data area
Runtime data is divided into two parts:  
Data area shared by multiple threads (created at JVM startup and destroyed at exit)  
Thread-private data area (created and destroyed with the thread)  


#### Data area shared by multiple threads
There are two types of data: class data and object data  
 
Class data includes field and method information, bytecode, and run-time constant pools, which are stored in the method area  
Object data is stored in the heap  



#### A thread-private data area
Used to assist in the execution of Java bytecode  
Each thread has its own PC register and Java virtual machine stack  
When the current method is a Java method, the PC register points to the address of the virtual machine instruction  
Otherwise, the current method is a local method and the value in the PC register is meaningless.  
The virtual machine stack consists of stack frames, which store the state of method execution, including local variable table, operand stack, etc.  
The local variable table and operand stack sizes are precomputed by the compiler and stored in method_info's code.  
The Java Virtual Machine specification states that each local variable can hold an int value or a reference value  




#### type system
Basic types include Boolean types and numeric types  
Numeric types can be divided into integer types and floating point types  
Reference types include class type, interface type, and reference type  


