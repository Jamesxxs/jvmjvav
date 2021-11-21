package src.main.me.Jamesxxs.jvav.classpath;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * WildcardEntry    CompositeEntry，         ，
 *           jar  ，
 * WildcardEntry   *
 * @author Jamesxxs
 */
public class WildcardEntry extends CompositeEntry{

    public WildcardEntry(String path) {
        this.compositeEntry = new ArrayList<>();
        String baseDir = path.substring(0,path.length()-1);

        //   golang filepath.Walk
        File file = new File(baseDir);

        for (File iter : Objects.requireNonNull(file.listFiles())){
            try {
                if(iter.isDirectory()&& !iter.getName().equals(baseDir)) {
                    System.out.println("skip this directory"+iter.getName());
                }
                if(iter.getName().endsWith(PATH_JAR_LOWER)||iter.getName().endsWith(PATH_JAR_UPPER)) {
                    ZipEntry jarEntry = new ZipEntry(iter.getAbsolutePath());
                    compositeEntry.add(jarEntry);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
