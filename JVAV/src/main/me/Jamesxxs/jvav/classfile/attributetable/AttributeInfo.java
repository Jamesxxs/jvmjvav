package src.main.me.Jamesxxs.jvav.classfile.attributetable;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;
import src.main.me.Jamesxxs.jvav.classfile.ConstantPool;

/**
 * @author Jamesxxs
 */
/*
attribute_info {
    u2 attribute_name_index;
    u4 attribute_length;
    u1 info[attribute_length];
}
*/
public interface AttributeInfo {
    /**
     * @param reader
     */
    void readInfo(ClassReader reader) throws Exception;

    /**
     *
     */
    static AttributeInfo[] readAttributes(ClassReader reader, ConstantPool cp) throws Exception {
        var attributesCount = reader.readUint16();
        var attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = readAttribute(reader, cp);
        }
        return attributes;
    }

    /**
     *
     */
    static AttributeInfo readAttribute(ClassReader reader, ConstantPool cp) throws Exception {
        //
        var attrNameIndex = reader.readUint16();
        //
        var attrName = cp.getUtf8(attrNameIndex);
        //
        var attrLen = reader.readUint32();
        //
        var attrInfo = newAttributeInfo(attrName, attrLen, cp);
        attrInfo.readInfo(reader);
        return attrInfo;
    }
    /**
     *
     */
    static AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantPool cp) {
        return switch (attrName) {
            case "Code" -> new CodeAttribute(cp);
            case "ConstantValue" -> new ConstantValueAttribute();
            case "Deprecated" -> new DeprecatedAttribute();
            case "Exceptions" -> new ExceptionsAttribute();
            case "LineNumberTable" -> new LineNumberTableAttribute();
            case "LocalVariableTable" -> new LocalVariableTableAttribute();
            case "SourceFile" -> new SourceFileAttribute(cp);
            case "Synthetic" -> new SyntheticAttribute();
            default -> new UnparsedAttribute(attrName, attrLen, new byte[]{});
        };
    }
}
