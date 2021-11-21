package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

/**
 * @author Jamesxxs
 */
public class ConstantFloatInfo implements ConstantInfo{
    public float val;

    @Override
    public void readInfo(ClassReader reader){
        val = reader.readUint32();
    }
    public float value(){
        return val;
    }
}