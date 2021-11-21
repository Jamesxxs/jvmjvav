package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

/**
 * @author Jamesxxs
 */
public class ConstantDoubleInfo  implements ConstantInfo{
    public double val;

    @Override
    public void readInfo(ClassReader reader){
        val = reader.readUint64();
    }
    public double value(){
        return val;
    }
}