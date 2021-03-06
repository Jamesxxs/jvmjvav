package src.main.me.Jamesxxs.jvav.classpath;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Jamesxxs
 */
public class DirEntry extends Entry{

    private final String absDir;
    DirEntry(String path){
        try {
            File directory = new File(path);
            absDir = directory.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    @Override
    public byte[] readClass(String className)  {
        String fileName = String.join(absDir,className);
        byte[] contents = null;
        try (FileInputStream in = new FileInputStream(fileName)) {
            contents = in.readAllBytes();
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
