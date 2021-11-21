package src.main.me.Jamesxxs.jvav.classfile.attributetable;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

/**
 * @author Jamesxxs
 *
 */
/*
ConstantValue_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 constantvalue_index;
}
*/
public class ConstantValueAttribute implements AttributeInfo {
    private int constValueIndex;

    @Override
    public void readInfo(ClassReader reader) {
        this.constValueIndex = reader.readUint16();
    }

    public int getConstValueIndex(){
        return constValueIndex;
    }
}
