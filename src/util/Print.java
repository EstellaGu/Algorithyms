package util;

import java.util.List;

public class Print {
    public static void print1DArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void print2DArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void print2DList(List<List<Integer>> list) {
        for (List<Integer> l: list) {
            for (int i: l) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
