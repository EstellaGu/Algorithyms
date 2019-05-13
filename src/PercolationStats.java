
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int T;
    private final int N;
    private double[] fractions;
    private double mean = -1.0;
    private double stddev = -1.0;
    private double confidenceLo = -1.0;
    private double confidenceHi = -1.0;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        T = trials;
        N = n;
        fractions = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation pcl = new Percolation(N);
            while (!pcl.percolates()) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);

                pcl.open(row, col);
            }

            int openSize = pcl.numberOfOpenSites();
            double ratio = (double) openSize / (N * N);
            fractions[i] = ratio;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean == -1.0) {    // 如果mean还没有被计算过，就计算一下
            mean = StdStats.mean(fractions);
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev == -1.0) {    // 如果stddev还没有被计算过，就计算一下
            stddev = StdStats.stddev(fractions);
        }

        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        if (confidenceLo == -1.0) {
            double m = mean();
            double std = stddev();
            confidenceLo = m - 1.96 * std / Math.sqrt(T);
        }
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (confidenceHi == -1.0) {
            double m = mean();
            double std = stddev();
            confidenceHi = m + 1.96 * std / Math.sqrt(T);
        }
        return confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {

        StdOut.printf("java-algs4 PercolationStats ");
        int n = StdIn.readInt();
        int trials = StdIn.readInt();

        PercolationStats pcls = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + pcls.mean());
        StdOut.println("stddev                  = " + pcls.stddev());
        StdOut.println("95% confidence interval = [" + pcls.confidenceLo() + ", " + pcls.confidenceHi() + "]");
    }
}
