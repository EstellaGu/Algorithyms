package daily.Stack;

import java.util.Stack;

/**
* 最小栈
* 在一个栈上实现push, pop, getMin操作，时间复杂度都为O(1)
* */

public class MinStack {
    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public MinStack() {
        stackData = new Stack<>();
        stackMin = new Stack<>();
    }

    public void push(int val) {
        stackData.push(val);

        if (stackMin.isEmpty()) {
            stackMin.push(val);
        } else {
            if (stackMin.peek() > val) {
                stackMin.push(val);
            }
        }
    }

    public int pop() {
        int toDelete = stackData.pop();

        if (toDelete == stackMin.peek()) {
            stackMin.pop();
        }

        return toDelete;
    }

    public int getMin() {
        return stackMin.peek();
    }
}
