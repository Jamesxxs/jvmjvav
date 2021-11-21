package src.main.me.Jamesxxs.jvav.classpath;

import java.io.File;

/**
 *
 * @author Jamesxxs
 */
public class ClassPath extends Entry{

    Entry bootClasspath;
    Entry extClasspath;
    Entry userClasspath;

    public ClassPath(String jreOption, String cpOption) throws Exception {
        //
        parseBootAndExtClasspath(jreOption);
        //
        parseUserClasspath(cpOption);
    }

    /**
     *
     * @param jreOption
     * @throws Exception
     *          jre\lib  ，         jre\lib\ext
     */
    private void parseBootAndExtClasspath(String jreOption) throws Exception {
        String jreDir = getJreDir(jreOption);

        // jre/lib/*
        //
        var jreLibPath = String.join(PATH_LIST_SEPARATOR,jreDir, "lib", "*");
        bootClasspath = new WildcardEntry(jreLibPath);

        // jre/lib/ext/*
        String jreExtPath = String.join(PATH_LIST_SEPARATOR,jreDir, "lib", "ext", "*");
        extClasspath = new WildcardEntry(jreExtPath);
    }

    String getJreDir(String jreOption) throws Exception {
        //         jre
        if(!"".equals(jreOption)&&exists(jreOption)){
            return jreOption;
        }
        //        jre
        if(exists("./jre")) {
            return "./jre";
        }
        //      JAVA_HOME
        var jh = System.getenv("JAVA_HOME");
        if (!"".equals(jh)){
            return String.join(PATH_LIST_SEPARATOR,jh,"jre");
        }
        throw new Exception("Can not find jre folder!");
    }

    boolean exists(String path)  {
        File file = new File(path);
        return file.exists();
    }

    void parseUserClasspath(String cpOption) {
        //    -cp
        if ("".equals(cpOption)) {
            cpOption = ".";
        }
        userClasspath = newEntry(cpOption);
    }

    /**
     *
     * @param className            class
     * @return
     */
    @Override
    public byte[] readClass(String className) {
        className = className+".class";
        byte[] tmp;
        if((tmp = bootClasspath.readClass(className)).length!=0){
            return tmp;
        }
        if((tmp = extClasspath.readClass(className)).length!=0){
            return tmp;
        }
        if((tmp = userClasspath.readClass(className)).length!=0){
            return tmp;
        }
        return null;
    }

    @Override
    public String string() {
        return userClasspath.string();
    }
}
