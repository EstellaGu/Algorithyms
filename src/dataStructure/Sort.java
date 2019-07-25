package dataStructure;


import util.Print;

/**
 * 各种排序算法实现
 * 都是从小到大排
 * */
public class Sort {
    /**
     * 归并排序
     * 时间复杂度：O(NlogN)
     * 空间复杂度：O(N)
     * @param arr  要排序的数组
     */
    public static void mergeSort(int[] arr) {
        int[] aux = new int[arr.length];   // 不能在递归函数中新建数组，否则开销会很大
        mergeSortHelper(arr, aux, 0, arr.length - 1);
    }

    private static void mergeSortHelper(int[] arr, int[] aux, int low, int high) {
        // 基线条件
        // low和high相等的时候表示只有一个元素要排序，直接返回就行了
        if (low >= high) return;

        int mid = (low + high) / 2; // mid;
        mergeSortHelper(arr, aux, low, mid);  // 排左边的一半
        mergeSortHelper(arr, aux, mid + 1, high); // 排右边的一半
        merge(arr, aux, low, mid, high); // 归并左右排好的
    }

    private static void merge(int[] arr, int[] aux, int low, int mid, int high) {
        // 归并两个已排好序的数组：low-mid, mid+1-high

        // 因为这个函数没有返回值，所以必须将在原数组排序
        for (int i = 0; i < arr.length; i++) {
            aux[i] = arr[i];
        }

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                arr[k] = aux[j++];
            } else if (j > high) {
                arr[k] = aux[i++];
            } else if (arr[i] < arr[j]){
                arr[k] = aux[i++];
            } else {
                arr[k] = aux[j++];
            }
        }
    }

    /**
     * 插入排序
     * 时间复杂度: O(N^2)
     * 空间复杂度: O(1)
     * */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int curr = arr[i];

            int j = i - 1;
            while (j >= 0 && arr[j] > curr) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = curr;
        }
    }

    /**
     * 选择排序
     * 时间复杂度: O(N^2)
     * 空间复杂度: O(1)
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }

            int tmp = arr[min];
            arr[min] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] test = {1, 2, 3, 2, 3, 8, 7, 3, 12, 34, 23};
        selectionSort(test);
        Print.print1DArray(test);
    }
}
