package nextProgram;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DecompressedString {

    public static String recursion(String s) {
        // 这个没问题了
        if (!s.contains("[")) {  // 基线条件
            return s;
        } else {   // 递归条件
            StringBuilder result = new StringBuilder();
            StringBuilder number = new StringBuilder();

            for (int i = 0; i < s.length(); i++) {
                char curr = s.charAt(i);
                if (curr >= 'a' && curr <= 'z'|| curr >= 'A' && curr <= 'Z') {
                    result.append(curr);
                } else if (curr >= '0' && curr <= '9') {
                    number.append(curr) ;
                } else if (curr == '[') {
                    // 找出当前这个[对应的]，对这两个[]之间的内容递归调用这个函数
                    int j = i + 1;
                    int count = 0;
                    while (true) {
                        if (s.charAt(j) == ']' && count == 0) {
                            break;
                        } else if (s.charAt(j) == ']' && count != 0) {
                            count--;
                        } else if (s.charAt(j) == '['){
                            count++;
                        }
                        j++;
                    }

                    StringBuilder tmp = new StringBuilder();
                    for (int k = 0; k < Integer.parseInt(number.toString()); k++) {
                        tmp.append(recursion(s.substring(i + 1, j)));
                    }

                    number.setLength(0);
                    i = j;
                    result.append(tmp);
                }
            }

            return result.toString();
        }
    }

    public static String stack(String s) {
        // 这个算法还是有问题
        Stack<Integer> numStack = new Stack<>();
        Stack<String> strStack = new Stack<>();
        StringBuilder toLoop = new StringBuilder();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);

            if (curr >= '0' && curr <= '9') {
                if (toLoop.length() != 0) {
                    strStack.push(toLoop.toString());
                    toLoop.setLength(0);
                }

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
                toLoop.append(curr);
            } else if (curr == ']'){
                // 获取当前循环次数
                numStack.pop();
                int number = numStack.pop();

                String tmp = toLoop.toString();
                for (int j = 0; j < number - 1; j++) {
                    toLoop.append(tmp);
                }

                if (!strStack.isEmpty()) {
                    String prev = strStack.pop();
                    toLoop = toLoop.insert(0, prev);
                }

                if (numStack.isEmpty()) {
                    result.append(toLoop);
                    toLoop.setLength(0);
                }
            }
        }

        if (toLoop.length() != 0) {
            result.append(toLoop);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(DecompressedString.recursion("3[z]2[2[y]pq4[2[jk]e1[f]]]ef"));
    }
}
