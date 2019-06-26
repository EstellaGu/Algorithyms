package eightPuzzle;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class Board {
    private int[][] blks;
    private int n;
    private int ham;
    private int manh;
    private int blankRow;
    private int blankCol;
    private Board twin;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }

        blks = blocks;
        n = blocks[0].length;

        ham = 0;
        manh = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blks[i][j] != 0 && get1D(i, j) != blks[i][j] - 1) {
                    ham++;
                    manh += Math.abs(i - get2DX(blks[i][j] - 1)) + Math.abs(j - get2DY(blks[i][j] - 1));
                }

                if (blks[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    private class BoardIterator<Board> implements Iterator<Board> {
        private Board[] neighbours = (Board[]) new Object[4];
        private int i = 0;

        public BoardIterator() {
            // 空格和上面的交换
            if (blankRow - 1 >= 0) {
                neighbours[i] = (Board) swapBlock(blankRow, blankCol, blankRow - 1, blankCol);
                i++;
            }

            // 空格和下面的交换
            if (blankRow + 1 < n) {
                neighbours[i] = (Board) swapBlock(blankRow, blankCol, blankRow + 1, blankCol);
                i++;
            }

            // 空格和左边的交换
            if (blankCol - 1 >= 0) {
                neighbours[i] = (Board) swapBlock(blankRow, blankCol, blankRow, blankCol - 1);
                i++;
            }

            // 空格和右边的交换
            if (blankCol + 1 < n) {
                neighbours[i] = (Board) swapBlock(blankRow, blankCol, blankRow, blankCol + 1);
                i++;
            }
        }

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Board next() {
            i--;
            return neighbours[i];
        }
    }

    private class BoardIterable<Board> implements Iterable {
        @Override
        public Iterator<Board> iterator() {
            return new BoardIterator<Board>();
        }
    }

    private int get1D(int row, int col) {
        return row * n + col;
    }

    private int get2DX(int idx) {
        return idx / n;
    }

    private int get2DY(int idx) {
        return idx % n;
    }

    private Board swapBlock(int row1, int col1, int row2, int col2) {
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = blks[i][j];
            }
        }

        int tmp = blocks[row1][col1];
        blocks[row1][col1] = blocks[row2][col2];
        blocks[row2][col2] = tmp;

        return new Board(blocks);
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        return ham;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manh;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return ham == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {

        if (twin == null) {
            int row;
            int col2;
            int col1;

            do {
                row = StdRandom.uniform(0, n);
                col1 = StdRandom.uniform(0, n);
                col2 = StdRandom.uniform(0, n);
            } while (col1 == col2 || blks[row][col1] == 0 || blks[row][col2] == 0);

            twin = swapBlock(row, col1, row, col2);
        }

        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (!(y instanceof Board)) {
            return false;
        }

        Board b = (Board) y;

        if (this.dimension() != b.dimension()) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blks[i][j] != b.blks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new BoardIterable<Board>();
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        String str = "" + n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str += blks[i][j] + "\t";
            }
            str += "\n";
        }
        return str;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] blocks = {
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}
        };

        Board b = new Board(blocks);
        System.out.println("Hamming: " + b.hamming());

        System.out.println();

        System.out.println("Manhattan: " + b.manhattan());

        System.out.println();

        System.out.println("Neighbours: ");
        Iterable<Board> itb = b.neighbors();
        Iterator<Board> itr = itb.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println();

        System.out.println("Twin: ");
        System.out.println(b.twin());

        System.out.println();

        System.out.println("isGoal: ");
        System.out.println(b.isGoal());
        System.out.println(b.equals(null));
    }
}
