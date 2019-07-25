package dataStructure;

import apple.laf.JRSUIUtils;

public class BinaryTree<Key extends Comparable<Key>> {
    private TreeNode root;

    private class TreeNode {
        Key val;
        TreeNode left;
        TreeNode right;

        public TreeNode(Key v) {
            val = v;
        }
    }

    private boolean less(Key k1, Key k2) {
        // k1 < k2, return true

        return k1.compareTo(k2) < 0;
    }

    public void insert(Key k) {
        if (root == null) {
            root = new TreeNode(k);
            return;
        }

        TreeNode ptr = root;

        while(true) {
            if (less(ptr.val, k) && ptr.right != null) {
                ptr = ptr.right;
            } else if (less(ptr.val, k) && ptr.right == null) {
                ptr.right = new TreeNode(k);
                break;
            } else if (less(k, ptr.val) && ptr.left != null) {
                ptr = ptr.left;
            } else if (less(k, ptr.val) && ptr.left == null) {
                ptr.left = new TreeNode(k);
                break;
            }
        }
    }


}
