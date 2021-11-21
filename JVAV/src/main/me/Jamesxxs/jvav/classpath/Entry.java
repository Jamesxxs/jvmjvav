package src.main.me.Jamesxxs.jvav.classpath;

import java.io.File;

/**
 * @author Jamesxxs
 */
public abstract class Entry {
    /**
     * Cross-platform separator
     */
    public static final String PATH_LIST_SEPARATOR = File.separator;

    /**
     * Dispel mana
     */
    public static final String PATH_ASTERISK = "*";
    public static final String PATH_JAR_LOWER = "jar";
    public static final String PATH_JAR_UPPER = "JAR";
    public static final String PATH_ZIP_LOWER = "zip";
    public static final String PATH_ZIP_UPPER = "ZIP";

    /**
     *
     * @param className File relative path
     * @return  byte[]
     */
    public abstract byte[] readClass(String className) ;

    /**
     *
     * @return String
     */
    public abstract String string();

    /**
     *
     * @param path class
     * @return Entry
     */
    public static Entry newEntry(String path){
        //
        if(path.contains(PATH_LIST_SEPARATOR)){
            return new CompositeEntry(path);
        }
        //
        if(path.endsWith(PATH_ASTERISK)){
            return new WildcardEntry(path);
        }
        //  jar
        if(path.endsWith(PATH_JAR_LOWER)||path.endsWith(PATH_JAR_UPPER)||path.endsWith(PATH_ZIP_LOWER)||path.endsWith(PATH_ZIP_UPPER)){
            return new ZipEntry(path);
        }
        return new DirEntry(path);
    }
}
