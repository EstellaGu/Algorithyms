package eightPuzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private int moves;
    private boolean solvable;
    private SearchNode goal;

    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode pre;
        private int manhPrio;
        private int hammPrio;
        private SearchNode self;

        private class SearchNodeIterator implements Iterator<SearchNode>{
            private Iterator<Board> it = board.neighbors().iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public SearchNode next() {
                return new SearchNode(it.next(), moves + 1, self);
            }
        }

        public SearchNode(Board b, int m, SearchNode p) {
            board = b;
            moves = m;
            pre = p;
            self = this;

            manhPrio = b.manhattan() + moves;
            hammPrio = b.hamming() + moves;
        }

        public int manhPriority() {
            return manhPrio;
        }

        public int hammPriority() {
            return hammPrio;
        }

        public SearchNode predecessor() {
            return pre;
        }

        public Iterator<SearchNode> neighbours() {
            return new SearchNodeIterator();
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        public Board getBoard() {
            return board;
        }

        public boolean equals(SearchNode n) {
            if (n == null) {
                return false;
            }

            return board.equals(n.getBoard());
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Board twin = initial.twin();
        moves = -1;

        SearchNode node1 = new SearchNode(initial, 0, null);
        goal = node1;
        SearchNode node2 = new SearchNode(twin, 0, null);

        Comparator<SearchNode> cmp = new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.manhPriority() != o2.manhPriority()) {
                    return o1.manhPriority() - o2.manhPriority();
                }

                if (o1.hammPriority() != o2.hammPriority()) {
                    return o1.hammPriority() - o2.hammPriority();
                }

                return 0;
            }
        };
        MinPQ<SearchNode> pq1 = new MinPQ<>(cmp);
        MinPQ<SearchNode> pq2 = new MinPQ<>(cmp);
        while (!node1.isGoal() && !node2.isGoal()) {    // 当这个节点不是目标节点时

            // 把它所有的邻接节点都加入优先队列
            Iterator<SearchNode> it1 = node1.neighbours();
            Iterator<SearchNode> it2 = node2.neighbours();
            while (it1.hasNext()) {
                SearchNode node = it1.next();
                Board tmp = node.getBoard();
                if (!node.equals(node1.predecessor())) {
                    pq1.insert(node);
                }
            }

            while (it2.hasNext()) {
                SearchNode node = it2.next();
                Board tmp = node.getBoard();
                if (!node.equals(node2.predecessor())) {
                    pq2.insert(node);
                }
            }

            node1 = pq1.delMin();
            node2 = pq2.delMin();
            goal = node1;
        }

        solvable = node1.isGoal();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;

        if (moves == -1) {
            Iterator<Board> it = solution().iterator();
            while (it.hasNext()) {
                Board tmp = it.next();
                moves++;
            }
        }

        return moves;
    }


    private class BoardIterator<Board> implements Iterator<Board> {
        private ResizingArrayStack<Board> path;
        private Iterator<Board> it;

        BoardIterator() {
            path = new ResizingArrayStack<>();

            SearchNode tmp = goal;
            while (tmp != null) {
                path.push((Board) tmp.getBoard());
                tmp = tmp.predecessor();
            }

            it = path.iterator();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Board next() {
            return it.next();
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new BoardIterator<Board>();
            }
        };
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        int[][] blocks = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };

        Board b = new Board(blocks);

        Solver s = new Solver(b);
        System.out.println("moves: " + s.moves());
        System.out.println("moves: " + s.moves());
        if (s.solution() != null) {
            System.out.println("solution: ");
            Iterator<Board> it = s.solution().iterator();
            while (it.hasNext()) {
                Board tmp = it.next();
                System.out.println(tmp);
            }
        } else {
            System.out.println("unsolvable");
        }

    }
}
