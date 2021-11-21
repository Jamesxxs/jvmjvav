package src.main.me.Jamesxxs.jvav.classfile;

import src.main.me.Jamesxxs.jvav.classfile.constinfotype.ConstantClassInfo;
import src.main.me.Jamesxxs.jvav.classfile.constinfotype.ConstantInfo;
import src.main.me.Jamesxxs.jvav.classfile.constinfotype.ConstantUtf8Info;
import src.main.me.Jamesxxs.jvav.classfile.constinfotype.ConstantNameAndTypeInfo;

/**
 *
 * @author Jamesxxs
 */
public class ConstantPool {


    // http://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.5
    // All 8-byte constants take up two entries in the constant_pool table of the class file.
    // If a CONSTANT_Long_info or CONSTANT_Double_info structure is the item in the constant_pool
    // table at index n, then the next usable item in the pool is located at index n+2.
    // The constant_pool index n+1 must be valid but is considered unusable.
    public ConstantInfo[] cp;
//    ConstantPool(){
//        constantPool = new ConstantInfo[cpCount];
//    }

    /**
     *
     *          1
     *       1--n
     *
     */
    public ConstantPool readConstantPool(ClassReader reader) throws Exception {
        //
        var cpCount = (int)(reader.readUint16());
        var tmpCp =  new ConstantPool(new ConstantInfo[cpCount]);

        // The constant_pool table is indexed from 1 to constant_pool_count - 1.
        for(var i = 1; i < cpCount; i++ ){
            tmpCp.cp[i] = ConstantInfo.readConstantInfo(reader, tmpCp);
            var tmp = tmpCp.cp[i].getClass().getName();
            tmp = tmp.substring(tmp.length()-18);
            switch (tmp) {
                // long   double
                //TODO: 2021/11/12
                case "e.ConstantLongInfo", "ConstantDoubleInfo" -> {
                    i++;
                }
                default -> {
                }
            }
        }
        return tmpCp;
    }

    ConstantPool() {
    }
    ConstantPool(ConstantInfo[] cp) {
        this.cp = cp;
    }

    /**
     *
     * @param index
     * @return
     * @throws Exception
     */
    public ConstantInfo getConstantInfo(int index) throws Exception {
        var ret = cp[index];
        if(ret!=null){
            return ret;
        }
        throw new Exception("Invalid constant pool index: %"+index+"!");
    }

    /**
     *
     * @param index
     * @return
     */
    public String getName(char index) throws Exception {
        var ntInfo = (ConstantNameAndTypeInfo)getConstantInfo(index);
        return getUtf8(ntInfo.nameIndex);
    }
    public String getType(char index) throws Exception {
        var ntInfo = (ConstantNameAndTypeInfo)getConstantInfo(index);
        return getUtf8(ntInfo.descriptorIndex);
    }
    /**
     *
     * @param
     */
    public String getClassName(int index) throws Exception {
        var classInfo = (ConstantClassInfo)getConstantInfo(index);
        return getUtf8(classInfo.nameIndex);
    }

    /**
     *        UTF-8
     * @param index
     * @return
     * @throws Exception
     */
    public String getUtf8(int index) throws Exception {
        ConstantUtf8Info utf8Info = (ConstantUtf8Info) getConstantInfo(index);
        return utf8Info.str;
    }







}
