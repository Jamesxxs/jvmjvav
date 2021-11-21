package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;
import src.main.me.Jamesxxs.jvav.classfile.ConstantPool;

/**
 *         ，
 * @author Jamesxxs
 */
public class ConstantStringInfo implements ConstantInfo{

    public ConstantPool cp;
    public int stringIndex;
    ConstantStringInfo(ConstantPool cp){
        this.cp = cp;
    }

    /**
     *
     * @param reader
     */
    @Override
    public void readInfo(ClassReader reader){
        stringIndex = reader.readUint16();
    }

    public String string() throws Exception {
        return cp.getUtf8(stringIndex);
    }
}
