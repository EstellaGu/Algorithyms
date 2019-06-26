package daily.Stack;

import java.util.Stack;

/**
 * 用一个辅助栈把栈里的内容排序
 * 只能使用一个辅助栈并且不能使用其他数据结构
 */
public class StackSort {

    public Stack<Integer> stackSort(Stack<Integer> stack) {
        Stack<Integer> helper = new Stack<>();

        while(!stack.isEmpty()) {
            // stack弹出最后一个元素top
            int top = stack.pop();

            // 如果helper为空，压入helper
            if (helper.isEmpty()) {
                helper.push(top);
            } else if(!helper.isEmpty() && top >= helper.peek()){
                // 如果helper不为空，且top小于等于helper栈顶元素，top压入helper
                helper.push(top);
            } else if (!helper.isEmpty() && top < helper.peek()) {
                // 如果helper不为空，且top大于helper栈顶元素，把helper栈顶元素弹出，压入stack，直到top小于等于helper栈顶元素，top压入helper

                while (!helper.isEmpty() && top < helper.peek()) {
                    stack.push(helper.pop());
                }

                helper.push(top);
            }

        }

        return helper;
    }
}
