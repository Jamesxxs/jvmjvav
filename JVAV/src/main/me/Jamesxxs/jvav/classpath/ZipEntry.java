package src.main.me.Jamesxxs.jvav.classpath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

/**
 * @author Jamesxxs
 */
public class ZipEntry extends Entry{
    private final String absDir;

    public ZipEntry(String path) {
        try {
            File directory = new File(path);
            //
            absDir = directory.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public byte[] readClass(String className) {
        //String fileName = String.join("",absDir,className);
        byte[] contents = null;
        //         ，
        try (FileInputStream in = new FileInputStream(absDir)) {
            try(ZipInputStream zipInputStream = new ZipInputStream(in);) {
                var nowEntry = zipInputStream.getNextEntry();
                while(nowEntry!=null){
                    if(nowEntry.getName().equals(className)){
                        contents = zipInputStream.readAllBytes();
                    }
                    //       Entry
                    zipInputStream.closeEntry();
                    nowEntry = zipInputStream.getNextEntry();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }

    @Override
    public String string() {
        return absDir;
    }
}
