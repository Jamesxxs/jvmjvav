package src.main.me.Jamesxxs.jvav.classfile;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Jamesxxs
 * @Description
 */
public class ClassReader {
    private final ByteBuffer buf;
    ClassReader(byte[] data){
        buf = ByteBuffer.allocate(data.length+5);
        buf.put(data);
        //   ，        put
        buf.rewind();
    }

    public byte readUint8() {
        return buf.get();
    }

    public char readUint16() {
        byte[] tmp = new byte[2];
        buf.get(tmp,0,2);
        return (char) (((tmp[0] & 0xFF) << 8) | (tmp[1] & 0xFF));
    }

    public int readUint32()  {
        byte[] tmp = new byte[4];
        buf.get(tmp,0,4);
        //
        return  ((tmp[3]&0xff) |((tmp[2]&0xff) << 8) | ((tmp[1]&0xff)  << 16) | ((tmp[0]&0xff) << 24));
    }

    public long readUint64() {
        byte[] tmp = new byte[8];
        buf.get(tmp,0,8);
        return  (((long)(tmp[0] & 0xFF) << 56) | ((long)(tmp[1] & 0xFF) << 48) | ((long)(tmp[2] & 0xFF) << 40)
                | ((long)(tmp[3] & 0xFF) << 32) |
                (tmp[4] & 0xFF << 24) | (tmp[5] & 0xFF << 16) | (tmp[6] & 0xFF << 8) | (tmp[7] & 0xFF));
    }
    /**
     *  uint16 ，
      */
    public char[] readUint16s() {
        var n = readUint16();
        char[] s = new char[n];
        for(int i=0;i<n;i++){
            s[i] = readUint16();
        }
        return s;
    }

    public byte[] readBytes(int n) {
        byte[] ret = new byte[n];
        buf.get(ret, 0, n);
        return ret;
    }
}
