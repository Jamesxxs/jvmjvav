package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

/**
 * @author Jamesxxs
 */
public class ConstantInvokeDynamicInfo implements ConstantInfo{

    public char bootstrapMethodAttrIndex;
    public char nameAndTypeIndex;

    @Override
    public void readInfo(ClassReader reader){
        bootstrapMethodAttrIndex = reader.readUint16();
        nameAndTypeIndex = reader.readUint16();
    }

}
