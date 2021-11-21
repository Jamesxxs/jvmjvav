package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

public class ConstantLongInfo implements ConstantInfo{
    public long val;

    @Override
    public void readInfo(ClassReader reader){
        val = reader.readUint64();
    }
    public long value(){
        return val;
    }
}