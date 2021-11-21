package src.main.me.Jamesxxs.jvav.classfile;

import src.main.me.Jamesxxs.jvav.classfile.attributetable.AttributeInfo;

/**
 *
 *              ，     ，   ，
 */
public class MemberInfo {

    /**
     *
     */
    private ConstantPool cp;
    private char accessFlags;
    private char nameIndex;
    private char descriptorIndex;
    private AttributeInfo[]    attributes;

    MemberInfo(ClassReader reader, ConstantPool cp) throws Exception {
        this.cp = cp;
        this.accessFlags = reader.readUint16();
        this.nameIndex = reader.readUint16();
        this.descriptorIndex = reader.readUint16();
        this.attributes = AttributeInfo.readAttributes(reader,cp);
    }
    /**
     *
     * @param
     * @return
     */
    public static MemberInfo[] readMembers(ClassReader reader,ConstantPool cp) throws Exception {
        var memberCount = reader.readUint16();
        MemberInfo[] members = new MemberInfo[memberCount];
        for(int i=0;i<memberCount;i++){
            members[i] = new MemberInfo(reader,cp);
        }
        return members;
    }

    public char getAccessFlags(){
        return accessFlags;
    }
    /**
     *
 */
    public String Name() throws Exception {
        return cp.getUtf8(nameIndex);
    }

    /**
     *
     * @return
     */
    public String Descriptor() throws Exception {
        return cp.getUtf8(descriptorIndex);
    }

}
