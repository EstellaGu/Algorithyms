package daily.Stack;

import java.util.LinkedList;

/**
 * 双端队列
 * 生成窗口最大值数组
 */
public class MaxWindowArray {
    public int[] getMaxWindowArray(int[] arr, int windowSize) {
        int[] res = new int[arr.length - windowSize + 1];
        // 双端队列queue，存放arr中的元素下标，第一个值指向的arr中的元素一定是当前窗口中最大的
        LinkedList<Integer> queue = new LinkedList<>();

        for (int i = 0; i < arr.length; i ++) {
            // 如果queue为空，i入队
            if (queue.isEmpty()) {
                queue.add(i);
            } else {
                // 如果queue不为空，比较arr[i]和queue最后一个元素arr[j]
                int j = queue.peekLast();
                while(arr[i] > arr[j] && !queue.isEmpty()) {
                    // 如果arr[i] > arr[j]，j出队，i入队，循环直到arr[i] <= arr[j]
                    queue.pollLast();

                    if (!queue.isEmpty()) {
                        j = queue.peekLast();
                    } else {
                        break; }

                }

                queue.add(i); // arr[i] <= arr[j]，i入队
            }

            // 如果queue[0] == i - windowSize，则弹出queue[0]
            if (queue.peek() == i - windowSize) {
                queue.poll();
            }

            // 如果i >= windowSize - 1，则开始记录最大值
            if (i >= windowSize - 1) {
                int index = queue.peek();
                res[i - windowSize + 1] = arr[index];
            }
        }

        return res;
    }
}
