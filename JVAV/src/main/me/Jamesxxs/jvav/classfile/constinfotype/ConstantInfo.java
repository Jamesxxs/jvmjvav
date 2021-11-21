package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;
import src.main.me.Jamesxxs.jvav.classfile.ConstantPool;

import java.io.IOException;


/**
 * @author Jamesxxs
 *
 *            ：
 *
 *            ，  ，
 * todo ugly code
 */

public interface ConstantInfo {
    //
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
     *
     *
     * @param reader
     */
    void readInfo(ClassReader reader) throws IOException;

    /**
     *   tag ，new      ，    readInfo
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
            //         SE7 invokedynamic
            //  ：                      ，
            case CONSTANT_METHOD_TYPE -> new ConstantMethodTypeInfo();
            case CONSTANT_METHOD_HANDLE -> new ConstantMethodHandleInfo();
            case CONSTANT_INVOKE_DYNAMIC -> new ConstantInvokeDynamicInfo();
            default -> throw new Exception("java.lang.ClassFormatError: constant pool tag!");
        };
        ret.readInfo(reader);
        return ret;
    }
}
