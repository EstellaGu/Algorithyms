
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        StdOut.print("k: ");

        int k = StdIn.readInt();

        StdOut.print("Sequence:");
        StdOut.println();

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            StdOut.print(queue.dequeue());
            StdOut.println();
        }

    }
}
