package src.main.me.Jamesxxs.jvav.classfile.constinfotype;

import src.main.me.Jamesxxs.jvav.util.ModifiedUtf8Charset;
import src.main.me.Jamesxxs.jvav.classfile.ClassReader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;

/**
 *       utf-8
 *  class        MUTF-8
 * @author Jamesxxs
 * todo mutf-8    utf-8
 */
public class ConstantUtf8Info implements ConstantInfo{
    public String str;

    @Override
    public void readInfo(ClassReader reader) throws IOException {
        int length = reader.readUint16();
        var bytes = reader.readBytes(length);
        //java.io.DataInputStream.readUTF()
        ByteBuffer tmpBuffer = ByteBuffer.allocate(bytes.length);
        tmpBuffer.put(bytes);
        //
        tmpBuffer.rewind();
        ModifiedUtf8Charset modifiedUtf8Charset = new ModifiedUtf8Charset();
        CharsetDecoder charsetDecoder = modifiedUtf8Charset.newDecoder();
        CharBuffer charBuffer = charsetDecoder.decode(tmpBuffer);
        str = charBuffer.toString();

    }
}
