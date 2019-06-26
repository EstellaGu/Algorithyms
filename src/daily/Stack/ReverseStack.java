package daily.Stack;

import java.util.Stack;

/**
 * 用递归把一个栈逆序
 */

public class ReverseStack {
    public Stack<Integer> reverse(Stack<Integer> stk) {
        if (stk.isEmpty()) {
            return stk;
        }

        int last = getAndRemoveLast(stk);
        reverse(stk);
        stk.push(last);

        return stk;
    }

    public int getAndRemoveLast(Stack<Integer> stk) {
        int result = stk.pop();

        if (stk.isEmpty()) {
            return result;
        } else {
            int last = getAndRemoveLast(stk);
            stk.push(result);
            return last;
        }

    }
}
