package util;

import java.util.LinkedList;
import java.util.Queue;

public class ConstructUtil {
    public static ListNode constructLinkedList(int[] nodes) {
        if (nodes.length == 0) return null;

        ListNode head = new ListNode(nodes[0]);
        ListNode ptr = head;
        for (int i = 1; i < nodes.length; i++) {
            ptr.next = new ListNode(nodes[i]);
            ptr = ptr.next;
        }

        return head;
    }

    public static void printLinkedList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    public static TreeNode constructTree(String[] nodes) {
        if (nodes.length == 0) return null;

        int N = nodes.length;
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> index = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        queue.add(root);
        index.add(0);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            int k = index.poll();

            if (2 * k + 1 < N && nodes[2 * k + 1] != null) {
                curr.left = new TreeNode(Integer.parseInt(nodes[2 * k + 1]));
                queue.add(curr.left);
                index.add(2 * k + 1);
            }

            if (2 * k + 2 < N && nodes[2 * k + 2] != null) {
                curr.right = new TreeNode(Integer.parseInt(nodes[2 * k + 2]));
                queue.add(curr.right);
                index.add(2 * k + 2);
            }
        }

        return root;
    }

    public static void printTree(TreeNode root) {

    }
}
