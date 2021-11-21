package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

/**
 * @author Jamesxxs
 */
public class ConstantMethodTypeInfo implements ConstantInfo{
    public char descriptorIndex;

    @Override
    public void readInfo(ClassReader reader){
        descriptorIndex = reader.readUint16();
    }
}
