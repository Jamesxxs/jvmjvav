package src.main.me.Jamesxxs.jvav.rtda;

/**
 * @author Jamesxxs
 *
 */
public class LocalVars {
    /**
     * The Java Virtual Machine specification states that each local variable can hold an int value or a reference value
     * float 32
     * int 32
     * short 16
     * long 64
     * double 64
     */
    Slot[] localVars;
    LocalVars(int maxLocals){
        localVars = new Slot[maxLocals];
        for(var i=0;i<maxLocals;i++){
            localVars[i] = new Slot();
        }
    }


    public void setInt(int index, int val) {
        localVars[index].num = val;
    }
    public int getInt(int index) {
        return localVars[index].num;
    }

    public void setFloat(int index , float val) {
        localVars[index].num =  Float.floatToIntBits(val);
    }
    public float getFloat(int index) {
        return Float.intBitsToFloat(localVars[index].num) ;
    }

    // long consumes two slots
    public void setLong(int index, long val) {
        localVars[index].num = (int)val;
        localVars[index+1].num = (int)(val >> 32);
    }
    public long getLong(int index)  {
       var low  = localVars[index].num;
        var high = localVars[index+1].num;
        return (((long)high)<<32)|(0x00000000FFFFFFFFL&low);
    }

    // double consumes two slots
    public void setDouble(int index, double val) {
        setLong(index,Double.doubleToLongBits(val));
    }
    public double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    public void setRef(int index, java.lang.Object obj) {
        localVars[index].ref = obj;
    }
    public java.lang.Object getRef(int index)  {
        return localVars[index].ref;
    }
}
