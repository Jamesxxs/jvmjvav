package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

/**
 * @author Jamesxxs
 */
public class ConstantMethodHandleInfo implements ConstantInfo{
    public byte referenceKind;
    public char referenceIndex;

    @Override
    public void readInfo(ClassReader reader){
        this.referenceKind = reader.readUint8();
        this.referenceIndex = reader.readUint16();
    }
}
