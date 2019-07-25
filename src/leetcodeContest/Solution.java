package leetcodeContest;

import java.util.*;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> results = new LinkedList<>();

        StringBuilder sb = new StringBuilder();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || i > 0 && nums[i - 1] != nums[i]) {
                int low = i + 1;
                int high = nums.length - 1;
                while (low < high) {
                    if (low + high + nums[i] == 0) {
                        List<Integer> triplet = new LinkedList<Integer>();
                        triplet.add(nums[i]);
                        triplet.add(nums[low]);
                        triplet.add(nums[high]);
                        results.add(triplet);
                    }

                    while (low < high && nums[low + 1] == nums[low]) {
                        low++;
                    }

                    while (low < high && nums[high - 1] == nums[high]) {
                        high--;
                    }

                    low++;
                    high--;
                }
            }
        }

        return results;
    }
}