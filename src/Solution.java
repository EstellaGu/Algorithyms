import edu.princeton.cs.algs4.In;
import util.ConstructUtil;
import util.Print;
import util.TreeNode;

import java.util.*;


class Solution {
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> seq = new ArrayList<>();
        inordered(root, seq, k);
        return seq.get(k - 1);
    }

    private void inordered(TreeNode root, List<Integer> result, int k) {
        if (root == null) return;

        inordered(root.left, result, k);
        result.add(root.val);
        if (result.size() == k) {
            return;
        }
        inordered(root.right, result, k);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        String[] nodes = {"3", "1", "4", null, "2"};
        TreeNode root = ConstructUtil.constructTree(nodes);
        System.out.println(s.kthSmallest(root, 3));
    }
}