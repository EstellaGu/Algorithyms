package nextProgram;

import java.util.Stack;

public class DecompressedString {

    public static String recursion(String sub) {
        if (!sub.contains("[")) {  // 基线条件
            return sub;
        } else {   // 递归条件
            StringBuilder result = new StringBuilder();
            StringBuilder number = new StringBuilder();

            for (int i = 0; i < sub.length(); i++) {
                if (sub.charAt(i) >= 'a' && sub.charAt(i) <= 'z') {
                    result.append(sub.charAt(i));
                } else if (sub.charAt(i) >= '0' && sub.charAt(i) <= '9') {
                    number.append(sub.charAt(i)) ;
                } else if (sub.charAt(i) == '[') {
                    // 找出当前这个[对应的]，对这两个[]之间的内容递归调用这个函数
                    int j = i + 1;
                    int count = 0;
                    while (true) {
                        if (sub.charAt(j) == ']' && count == 0) {
                            break;
                        } else if (sub.charAt(j) == ']' && count != 0) {
                            count--;
                        } else if (sub.charAt(j) == '['){
                            count++;
                        }
                        j++;
                    }

                    StringBuilder tmp = new StringBuilder();
                    for (int k = 0; k < Integer.parseInt(number.toString()); k++) {
                        tmp.append(recursion(sub.substring(i + 1, j)));
                    }

                    number.setLength(0);
                    i = j;
                    result.append(tmp);
                }
            }

            return result.toString();
        }
    }

    public static String stack(String str) {
        Stack<Integer> numStack = new Stack<>();
        StringBuilder queue = new StringBuilder();
        StringBuilder toLoop = new StringBuilder();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char curr = str.charAt(i);

            if (curr >= '0' && curr <= '9') {
                // 把queue追加到result
                result.append(queue);
                queue.setLength(0);

                // 把当前数字入栈，但要检查一下当前栈顶是否是有效数字，如果是，则说明这是个多位数
                int top;
                if (!numStack.isEmpty() && numStack.peek() != -1) {
                    top = numStack.pop() * 10 + curr - 48;
                } else {
                    top = curr - 48;
                }
                numStack.push(top);
            } else if (curr == '[') {
                // 在数字栈中标记一下
                numStack.push(-1);
            } else if (curr >= 'a' && curr <= 'z') {
                queue.append(curr);
            } else if (curr == ']'){
                // 获取当前循环次数
                numStack.pop();
                int number = numStack.pop();

                toLoop.append(queue);
                queue.setLength(0);

                String tmp = toLoop.toString();
                for (int j = 0; j < number - 1; j++) {
                    toLoop.append(tmp);
                }

                if (numStack.isEmpty()) {
                    result.append(toLoop);
                    toLoop.setLength(0);
                }
            }
        }

        result.append(queue);

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(DecompressedString.stack("10[a]"));
    }
}
