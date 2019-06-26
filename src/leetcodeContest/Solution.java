package leetcodeContest;

import java.util.*;

public class Solution {
    class MountainArray {
        int[] arr;

        public MountainArray(int[] a) {
            arr = a;
        }

        public int length() {
            return arr.length;
        }

        public int get(int index) {
            return arr[index];
        }
    }

    public int findInMountainArray(int target, MountainArray mountainArr) {
        int top = findTop(mountainArr);

        int left = incBinarySearch(mountainArr, 0, top, target);
        int right = decBinarySearch(mountainArr, top, mountainArr.length() - 1, target);

        System.out.println(left);
        System.out.println(right);

        if (left != -1) {
            return left;
        }

        return right;
    }

    public int findTop(MountainArray mountainArr) {
        int len = mountainArr.length();
        int top = 0;
        int low = 0;
        int high = len - 1;
        while(true) {
            top = (low + high) / 2;
            int topNum = mountainArr.get(top);
            int topPrev = mountainArr.get(top - 1);
            int topNext = mountainArr.get(top + 1);

            if (topNum >topPrev && topNum > topNext) {
                break;
            } else if (topNum > topPrev) {
                low = top;
            } else if (topNum < topPrev) {
                high = top;
            }
        }

        return top;
    }

    public int incBinarySearch(MountainArray mountainArr, int low, int high, int target) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;
        int midNum = mountainArr.get(mid);
        if (target == midNum) {
            return mid;
        } else if (target > midNum) {
            return incBinarySearch(mountainArr, mid + 1, high, target);
        } else {
            return incBinarySearch(mountainArr, low, mid - 1, target);
        }
    }

    public int decBinarySearch(MountainArray mountainArr, int low, int high, int target) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;
        int midNum = mountainArr.get(mid);
        if (target == midNum) {
            return mid;
        } else if (target < midNum) {
            return decBinarySearch(mountainArr, mid + 1, high, target);
        } else {
            return decBinarySearch(mountainArr, low, mid - 1, target);
        }
    }

    public void test() {
//        int[][] test = {
//                {3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1},
//                {1, 3, 2},
//                {1, 2, 3, 4, 5, 6, 5},
//                {3, 4, 9, 8, 7, 6, 5, 4, 3, 2, 1},
//                {1, 4, 5, 7, 9, 10, 5},
//                {2, 9, 8, 7, 6, 5}
//        };
//
//        int[] result = {4, 1, 5, 2, 5, 1};

//        for (int i = 0; i < test.length; i++) {
//            MountainArray array = new MountainArray(test[i]);
//            int r = findTop(array);
//
//            System.out.println("Test #" + (i + 1));
//            if (r == result[i]) {
//                System.out.println("==> passed");
//            } else {
//                System.out.println("==> failed");
//            }
//        }

        int[] test = {3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1};
        MountainArray array = new MountainArray(test);
        System.out.println(findInMountainArray(2, array));

    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.test();
    }
}
