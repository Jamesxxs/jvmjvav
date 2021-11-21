package src.main.me.Jamesxxs.jvav.classfile.attributetable;

import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

/**
 * @author Jamesxxs
 *
 */
public class ExceptionsAttribute implements AttributeInfo {
    private char[] exceptionIndexTable;

    @Override
    public void readInfo(ClassReader reader) throws Exception {
        exceptionIndexTable = reader.readUint16s();
    }
    public char[] exceptionIndexTable(){
        return exceptionIndexTable;
    }
}
