package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;
import src.main.me.Jamesxxs.jvav.classfile.ConstantPool;

import java.io.IOException;

/**
 * @author Jamesxxs
 *
 */
public class ConstantMemberRefInfo implements ConstantInfo{
    public ConstantPool cp;
    public int classIndex;
    public int nameAndTypeIndex;
    ConstantMemberRefInfo(ConstantPool cp){
        this.cp = cp;
    }

    @Override
    public void readInfo(ClassReader reader) throws IOException {
        classIndex = reader.readUint16();
        nameAndTypeIndex = reader.readUint16();
    }

    public String className() throws Exception {
        return cp.getClassName(classIndex);
    }

}
