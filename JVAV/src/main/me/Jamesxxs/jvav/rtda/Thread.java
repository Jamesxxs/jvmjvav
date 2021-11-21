package src.main.me.Jamesxxs.jvav.rtda;

import java.awt.*;

/**
 * @author Jamesxxs
 * Thread-private data areas
 * Each thread has its own PC register and Java virtual machine stack
 * When the current method is a Java method, the PC register points to the address of the virtual machine instruction
 * Otherwise, the current method is a local method and the value in the PC register is meaningless.
 *
 */
public class Thread {
    /**
     * Instruction register
     */
    private int pc;
    /**
     The virtual machine stack
     Specify the size of the virtual machine stack with the -xSS option
     The virtual machine stack consists of stack frames, which store the state of method execution, including local variable table, operand stack, etc.
     */
    private JVMStack stack;
    Thread(){
        stack = new JVMStack(1024);

    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }
    public void pushFrame(JVMFrame frame){
        stack.push(frame);
    }
    public JVMFrame popFrame() throws Exception {
        return stack.pop();
    }
    public JVMFrame currentFrame() throws Exception {
        return stack.top();
    }

}
