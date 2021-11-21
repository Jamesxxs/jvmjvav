package src.main.me.Jamesxxs.jvav.classfile;

import src.main.me.Jamesxxs.jvav.classfile.attributetable.AttributeInfo;

/**
 * @author Jamesxxs
 * @Description class
 */
public class ClassFile {


    /**
     *
     */
    public int minorVersion;
    public int majorVersion;
    public ConstantPool constantPool;
    public int accessFlags;
    public char thisClass;
    public char superClass;
    public char[] interfaces;
    public MemberInfo[] fields;
    public MemberInfo[] methods;
    public AttributeInfo[] attributes;

    public ClassFile(byte[] classData) throws Exception {
        /**
         *
         */
        ClassReader cr = new ClassReader(classData);
        read(cr);
    }

//    static ClassFile parser(byte[] classData) throws Exception {
//        var cr = new ClassReader(classData);
//        var cf = new ClassFile();
//
//    }

    void read(ClassReader reader) throws Exception {
        //
        readAndCheckMagic(reader);
        //
        readAndCheckVersion(reader);
        //
        constantPool = new ConstantPool().readConstantPool(reader);
        //      bitmask
        accessFlags = reader.readUint16();
        /*
         *       ，thisClass
         * superClass  Object.class  0，
         */
        thisClass = reader.readUint16();
        superClass = reader.readUint16();
        //     ，
        interfaces = reader.readUint16s();
        //
        fields = MemberInfo.readMembers(reader, constantPool);
        //
        methods = MemberInfo.readMembers(reader, constantPool);
        //
        attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    /**
     *
     * @param reader
     * @throws Exception
     */
    public void readAndCheckMagic(ClassReader reader) throws Exception {
        var magic = reader.readUint32();
        if(magic!=(int)0xCAFEBABE){
            throw new Exception("java.lang.ClassFormatError: magic!");
        }
    }

    /**
     *
     * @param reader
     * @throws Exception
     */
    public void readAndCheckVersion(ClassReader reader) throws Exception {
        minorVersion = reader.readUint16();
        majorVersion = reader.readUint16();
        switch (majorVersion){
            case 45:
                return;
            case 46,47,48,49,50,51,52:
                if(minorVersion==0) {
                    return;
                }
            default:break;
        }
        throw new Exception("java.lang.UnsupportedClassVersionError!");
    }



    /**
     *
     * 16  bitmask，  class           、
     *
     * @return
     */


    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public MemberInfo[] getFields() {
        return fields;
    }

    public MemberInfo[] getMethods() {
        return methods;
    }
    /**
     *
     */
    public String getClassName() throws Exception {
        return constantPool.getClassName(thisClass);
    }

    /**
     *
     * @return
     */
    public String getSuperClassName() throws Exception {
        if(superClass>0){
            return constantPool.getClassName(superClass);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String[] getInterfaceNames() throws Exception {
        String[] ret = new String[interfaces.length];
        for(int i=0;i<interfaces.length;i++){
            ret[i] = constantPool.getClassName(interfaces[i]);
        }
        return ret;
    }
}
