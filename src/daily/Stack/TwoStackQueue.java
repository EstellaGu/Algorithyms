package daily.Stack;

import java.util.Stack;

/**
 * 用两个栈模拟一个队列
 */
public class TwoStackQueue {
    private Stack<Integer> stackPush;
    private Stack<Integer> stackPop;

    public TwoStackQueue() {
        stackPush = new Stack<>();
        stackPop = new Stack<>();
    }

    public void add(int val) {
        stackPush.push(val);
    }

    public int poll() {
        makeQueue();

        return stackPop.pop();
    }

    public int peek() {
        makeQueue();

        return stackPop.peek();
    }

    private void makeQueue(){
        if (stackPush.isEmpty() && stackPop.isEmpty()) {
            throw new RuntimeException("No number in the queue");
        } else if (stackPop.isEmpty()) {
            while(!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
    }
}
