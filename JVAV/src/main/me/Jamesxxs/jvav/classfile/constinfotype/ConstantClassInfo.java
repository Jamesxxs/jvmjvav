package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;
import src.main.me.Jamesxxs.jvav.classfile.ConstantPool;

import java.io.IOException;

/**
 *
 * @author Jamesxxs
 */
public class ConstantClassInfo implements ConstantInfo{
    public ConstantPool cp;
    public int nameIndex;
    ConstantClassInfo(ConstantPool cp){
        this.cp = cp;
    }
    @Override
    public void readInfo(ClassReader reader) throws IOException {
        nameIndex = reader.readUint16();
    }
    public String name() throws Exception {
        return cp.getUtf8(nameIndex);
    }
}
