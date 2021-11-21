package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

/**
 * @author Jamesxxs
 */
public class ConstantIntegerInfo implements ConstantInfo{
    public int val;

    @Override
    public void readInfo(ClassReader reader){
        val = reader.readUint32();
    }
    public int Value(){
        return val;
    }
}
