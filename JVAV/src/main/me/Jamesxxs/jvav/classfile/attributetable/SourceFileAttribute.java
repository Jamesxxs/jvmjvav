package src.main.me.Jamesxxs.jvav.classfile.attributetable;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;
import src.main.me.Jamesxxs.jvav.classfile.ConstantPool;

/**
 * @author Jamesxxs
 *       ，
 *
 *
 */
/*
SourceFile_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 sourcefile_index;
}
*/
public class SourceFileAttribute implements AttributeInfo {
    private ConstantPool cp;
    private int sourceFileIndex;
    public SourceFileAttribute(ConstantPool cp) {
        this.cp = cp;
    }
    @Override
    public void readInfo(ClassReader reader){
        this.sourceFileIndex = reader.readUint16();
    }
    public String fileName() throws Exception {
        return this.cp.getUtf8(sourceFileIndex);
    }

}
