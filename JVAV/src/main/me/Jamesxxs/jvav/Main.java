package src.main.me.Jamesxxs.jvav;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import src.main.me.Jamesxxs.jvav.classpath.ClassPath;
import src.main.me.Jamesxxs.jvav.rtda.JVMFrame;
import src.main.me.Jamesxxs.jvav.rtda.LocalVars;
import src.main.me.Jamesxxs.jvav.rtda.OperandStack;
import src.main.me.Jamesxxs.jvav.classfile.ClassFile;

import java.util.Arrays;

/**
 * Start the class
 * @author Jamesxxs
 * @version
 */
class Main {
    public static void main(String[] args) throws Exception {
        Cmd cmd = new Cmd();
        JCommander jCommander = JCommander.newBuilder().addObject(cmd).build();
        jCommander.parse(args);
        if(args.length>0){
            cmd._class = cmd.getRelease().get(0);
            System.arraycopy(cmd.getRelease().toArray(), 1,cmd.args, 0, cmd.getRelease().size()-1);
        }
        if (cmd.getVersionFlag()) {
            System.out.println("version 0.0.1");
        } else if(cmd.getHelpFlag() || cmd._class==null || "".equals(cmd._class)) {
            cmd.printUsage();
        } else {
            startJVM(cmd);
        }
    }
    public static void startJVM(Cmd cmd) throws Exception {
        var frame = new JVMFrame(100,100);
        testLocalVars(frame.getLocalVars());
        testOperandStack(frame.getOperandStack());
    }

    public static void testLocalVars(LocalVars vars) {
        vars.setInt(0, 100);
        vars.setInt(1, -100);
        vars.setLong(2, 2997924580L);
        vars.setLong(4, -2997924580L);
        vars.setFloat(6, 3.1415926F);
        vars.setDouble(7, 2.71828182845);
        vars.setRef(9, null);
        System.out.println((vars.getInt(0)));
        System.out.println((vars.getInt(1)));
        System.out.println((vars.getLong(2)));
        System.out.println((vars.getLong(4)));
        System.out.println((vars.getFloat(6)));
        System.out.println((vars.getDouble(7)));
        System.out.println((vars.getRef(9)));
    }

    public static void testOperandStack(OperandStack ops) {
        ops.pushInt(100);
        ops.pushInt(-100);
        ops.pushLong(2997924580L);
        ops.pushLong(-2997924580L);
        ops.pushFloat(3.1415926F);
        ops.pushDouble(2.71828182845D);
        ops.pushRef(null);
        System.out.println((ops.popRef()));
        System.out.println(ops.popDouble());
        System.out.println(ops.popFloat());
        System.out.println(ops.popLong());
        System.out.println(ops.popLong());
        System.out.println(ops.popInt());
        System.out.println(ops.popInt());
    }

    private static void printClassInfo(ClassFile cf) throws Exception {

        System.out.printf("version: %d.%d\n", cf.majorVersion, cf.minorVersion);
        System.out.printf("constants count: %d\n", cf.constantPool.cp.length);
        System.out.printf("access flags: %d\n", cf.accessFlags);
        System.out.printf("this class: %s\n", cf.getClassName());
        System.out.printf("super class: %s\n", cf.getSuperClassName());
        System.out.printf("interfaces: %s\n", Arrays.toString(cf.getInterfaceNames()));
        System.out.printf("fields count: %d\n", cf.fields.length);
        for(var f:cf.fields) {
            System.out.printf("  %s\n", f.Name());
        }
        System.out.printf("methods count: %d\n", cf.methods.length);
        for(var m:cf.methods) {
            System.out.printf("%s\n", m.Name());
        }
    }

    public static ClassFile loadClass(String className, ClassPath cp) throws Exception {
        var classData = cp.readClass(className);
        return new ClassFile(classData);
    }

}