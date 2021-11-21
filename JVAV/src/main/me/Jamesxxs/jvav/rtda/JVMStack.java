package src.main.me.Jamesxxs.jvav.rtda;

/**
 * @author Jamesxxs
 */
public class JVMStack {
    int maxSize;
    int size;
    /**
     * The top pointer stacks are connected using linked lists
     */
    JVMFrame top;
    JVMStack(int size){
        this.size = size;
    }
    public void push(JVMFrame frame){
        if(size>=maxSize){
            throw  new StackOverflowError();
        }
        if(top==null){
            frame.next = top;
        }
        top = frame;
        size++;
    }
    public JVMFrame pop() throws Exception {
        if(top==null){
            throw new Exception("jvm stack is empty!");
        }
        var nowTop = top;
        top = top.next;
        nowTop.next = null;
        size--;
        return nowTop;
    }
    public JVMFrame top() throws Exception {
        if(top==null){
            throw new Exception("jvm stack is empty!");
        }
        return top;
    }
}
