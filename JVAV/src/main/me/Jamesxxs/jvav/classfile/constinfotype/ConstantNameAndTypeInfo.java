package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

import java.io.IOException;

/**
 * @author Jamesxxs
 */
public class ConstantNameAndTypeInfo implements ConstantInfo{
    public int nameIndex;
    public int descriptorIndex;

    @Override
    public void readInfo(ClassReader reader) throws IOException {
        nameIndex = reader.readUint16();
        descriptorIndex = reader.readUint16();
    }
}
