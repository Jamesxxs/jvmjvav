package src.main.me.Jamesxxs.jvav.rtda;

/**
 * @author Jamesxxs
 */
public class JVMFrame {
    /**
     *
     */
    JVMFrame next;
    /**
     * Local variable table The local variable table and operand stack sizes are precomputed by the compiler and stored in method_info's code
     */
    LocalVars localVars;
    /**
     * The operand stack
     */
    OperandStack operandStack;

    public JVMFrame(int maxLocals, int maxStack){
        localVars = new LocalVars(maxLocals);
        operandStack = new OperandStack(maxStack);
    }
    public LocalVars getLocalVars(){
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }
}
