package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private Boolean[][] isOpen;
    private final int N;
    private int openSize;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        // uf: n * n是实际的网格，最后两个是虚拟网网格
        // 第n * n个连接最上面那排网格
        // 第n * n + 1个连接最下面那排网格
        uf = new WeightedQuickUnionUF(n * n + 2);

        // 一开始所有的格子都是block状态
        isOpen = new Boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                isOpen[i][j] = false;
            }
        }

        N = n;
        openSize = 0;
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N) {
            throw new IllegalArgumentException();
        }
    }

    private int to1D(int row, int col) {
        return N * (row - 1) + col - 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        // 检测参数合法性
        validate(row, col);

        if (!isOpen[row][col]) {  // 如果这个格子没有被打开的话
            // 设置open状态
            isOpen[row][col] = true;
            openSize++;

            // 联结周围的格子
            if (row - 1 > 0 && isOpen[row - 1][col]) {
                // 联结这个格子和它上面的那个格子
                uf.union(to1D(row, col), to1D(row - 1, col));
            }

            if (row + 1 < N + 1 && isOpen[row + 1][col]) {
                // 联结这个格子和它下面的那个格子
                uf.union(to1D(row, col), to1D(row + 1, col));
            }

            if (col - 1 > 0 && isOpen[row][col - 1]) {
                // 联结这个格子和它左边的那个格子
                uf.union(to1D(row, col), to1D(row, col - 1));
            }

            if (col + 1 < N + 1 && isOpen[row][col + 1]) {
                // 联结这个格子和它右边的那个格子
                uf.union(to1D(row, col), to1D(row, col + 1));
            }

            // 如果这个格子是最上面那排的，则要连接它和n * n
            if (row == 1) {
                uf.union(to1D(row, col), N * N);
            }

            // 如果这个格子是最下面那排的，则要连接它和n * n + 1
            if (row == N) {
                uf.union(to1D(row, col), N * N + 1);
            }

        }

        // 如果这个格子已经被打开了，那么什么也不做
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return isOpen[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        return uf.connected(N * N, to1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(N * N, N * N + 1);
    }

    public static void main(String[] args) {
        Percolation pcl = new Percolation(6);

        pcl.open(1, 6);
        pcl.open(2, 6);
        pcl.open(3, 6);
        pcl.open(4, 6);
        pcl.open(5, 6);

        System.out.print(pcl.percolates());

    }
}
