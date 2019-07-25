package dataStructure;

import util.Print;

public class MaxHeap {
    private int[] heap;
    private int N;
    private int capacity;

    public MaxHeap() {
        heap = new int[2];
        capacity = -1;
        N = 0;
    }

    public MaxHeap(int size) {
        heap = new int[size + 1];
        capacity = size;
        N = 0;
    }

    public MaxHeap(int[] keys) {
        capacity = -1;
        N = keys.length;
        heap = new int[N + 1];
        for (int i = 0; i < N; i++) {
            heap[i + 1] = keys[i];
        }

        for (int k = N / 2; k >= 1; k--) {
            sink(k);
        }
    }

    private void resize(int cap) {
        int[] newHeap =  new int[cap];

        for (int i = 1; i <= N; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
        newHeap = null;
    }

    private void swap(int k1, int k2) {
        int tmp = heap[k1];
        heap[k1] = heap[k2];
        heap[k2] = tmp;
    }

    private void swim(int k) {
        if (k < 1 || k > N) {
            throw new IllegalArgumentException();
        }

        while (k > 1 && heap[k / 2] < heap[k]) {
            swap(k, k / 2);
            k /= 2;
        }
    }

    private void sink(int k) {
        if (k < 1 || k > N) {
            throw new IllegalArgumentException();
        }

        while (2 * k <= N) {
            if (2 * k + 1 <= N && heap[2 * k] < heap[2 * k + 1] && heap[k] < heap[2 * k + 1]) {
                swap(k, 2 * k + 1);
                k = 2 * k + 1;
            } else if (heap[k] < heap[2 * k]){
                swap(k, 2 * k);
                k = 2 * k;
            } else {
                break;
            }
        }

    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean isFull() {
        if (capacity == -1) return false;  // 用户不限制容量时堆永远不可能满

        return N == capacity;
    }

    public void insert(int key) {
        if (isFull()) {
            throw new RuntimeException();
        }

        if (N == heap.length - 1) {
            resize(2 * heap.length);
        }

        heap[++N] = key;

        swim(N);
    }

    public int getMax() {
        if (isEmpty()) {
            throw new RuntimeException();
        }

        return heap[1];
    }

    public int deleteMax(){
        if (isEmpty()) {
            throw new RuntimeException();
        }

        int max = heap[1];
        heap[1] = heap[N--];
        heap[N + 1] = 0;
        if (N != 0)
            sink(1);

        if (N <= heap.length / 4) {
            resize(N + 1);
        }

        return max;
    }

    public int size() {
        return N - 1;
    }

    public static void main(String[] args) {
        int[] test = {14, 15, 12, 35, 13, 17};
        MaxHeap heap = new MaxHeap(test);
        System.out.println(heap.isEmpty());  // true
        heap.insert(0);
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(4);
        heap.insert(5);
        heap.insert(6);
        heap.insert(7);

        System.out.println(heap.isEmpty());   // false
        System.out.println(heap.isFull());    // false

        while (!heap.isEmpty()) {
            System.out.println(heap.deleteMax());
        }

//        heap.insert(9);
//        System.out.println(heap.deleteMax());
    }
}
