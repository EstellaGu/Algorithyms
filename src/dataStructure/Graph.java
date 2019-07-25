package dataStructure;

import util.Print;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 目前只是无向无权图
 */
public class Graph {
    private int[][] graph;   // 没有边的地方用0表示
    private int N;    // N代表节点数
    private boolean weighted;
    private boolean bidirectional;

    public Graph(int[][] g, boolean weight, boolean bi) {
        graph = g;
        N = g.length;
        weighted = weight;
        bidirectional = bi;
    }

    public void addEdge(int vStart, int vEnd, int w) {
        if (w <= 0 || vStart < 0 || vStart >= N || vEnd < 0 || vEnd >= N) {
            throw new IllegalArgumentException();
        }

        graph[vStart][vEnd] = w;
    }

    public void deleteEdge(int vStart, int vEnd) {
        if (vStart < 0 || vStart >= N || vEnd < 0 || vEnd >= N) {
            throw new IllegalArgumentException();
        }

        graph[vStart][vEnd] = 0;
    }

    public int[] DFS(int start) {
        boolean[] visited  = new boolean[N];
        int[] result = new int[N];
        Stack<Integer> stack = new Stack<>();

        visited[start] = true;
        result[0] = start;
        stack.push(start);
        int count = 1;
        while (!stack.isEmpty()) {
            int curr = stack.peek();
            boolean hasUnvisitedNeighbour = false;
            for (int i = 0; i < N; i++) {
                if (graph[curr][i] != 0 && !visited[i]) {
                    visited[i] = true;
                    stack.push(i);
                    result[count] = i;
                    count++;
                    hasUnvisitedNeighbour = true;
                    break;
                }
            }

            if (!hasUnvisitedNeighbour) curr = stack.pop();
        }

        return result;
    }

    // 这个还有问题
    private int[] DFSHelper(int start, int[] result, int count, boolean[] visited) {
        visited[start] = true;
        result[count] = start;
        boolean hasUnvisitedNeighbour = false;
        for (int i = 0; i < N; i++) {
            if (graph[start][i] != 0 && !visited[i]) {
                hasUnvisitedNeighbour = true;
                DFSHelper(i, result, count + 1, visited);
            }
        }

        if (!hasUnvisitedNeighbour) {
            return result;
        }

        return result;
    }

    public int[] BFS(int start) {
        int[] result = new int[N];
        boolean[] visited = new boolean[N];
        Queue<Integer> queue = new LinkedList<>();
        int count = 1;

        queue.add(start);
        visited[start] = true;
        result[0] = start;
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (int i = 0; i < N; i++) {
                if (graph[curr][i] != 0 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                    result[count] = i;
                    count++;
                }
            }
        }

        return result;
    }

    public int[][] shortestPath(int start) {
        return weighted ? dijkstra(start) : unweightedShortestPath(start);
    }

    private int[][] dijkstra(int start) {
        int[] dist = new int[N];
        int[] path = new int[N];
        boolean[] collected = new boolean[N];

        for (int i = 0; i < N; i++) {
            dist[i] = Integer.MAX_VALUE;
            path[i] = -1;
        }

        dist[start] = 0;
        while (true) {
            int min = minDist(dist, collected);

            if (min == -1) {
                break;
            }

            collected[min] = true;
            for (int i = 0; i < N; i++) {
                if (graph[min][i] != 0 && !collected[i] && dist[i] > dist[min] + graph[min][i]) {
                    dist[i] = dist[min] + graph[min][i];
                    path[i] = min;
                }
            }
        }

        int[][] result = new int[2][N];
        result[0] = dist;
        result[1] = path;

        return result;
    }

    private int minDist(int[] dist, boolean[] collected) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            if (!collected[i] && dist[i] < min) {
                min = i;
            }
        }

        if (min == Integer.MAX_VALUE) return -1;

        return min;
    }

    private int[][] unweightedShortestPath(int start) {
        int[] dist = new int[N];
        int[] path = new int[N];
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            dist[i] = -1;
            path[i] = -1;
        }

        dist[start] = 0;
        queue.add(start);



        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (int i = 0 ; i < N; i++) {
                if (graph[curr][i] != 0 && dist[i] == -1) {
                    dist[i] = dist[curr] + 1;
                    path[i] = curr;
                    queue.add(i);
                }
            }
        }

        int[][] result = new int[2][N];
        result[0] = dist;
        result[1] = path;
        return result;
    }


    public int[] topology() {
        if (bidirectional) return null;

        int[] result = new int[N];
        int ptr = 0;
        Queue<Integer> queue = new LinkedList<>();
        // 计算所有点的入度
        int[] inDegree = new int[N];
        for (int i = 0; i < N; i++) {
            int count = 0;
            for (int j = 0; j < N; j++) {
                if (graph[j][i] != 0) count++;
            }
            inDegree[i] = count;
        }

        // 把所有入度为0的点入队
        for (int i = 0; i < N; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            result[ptr] = curr;
            ptr++;

            for (int i = 0; i < N; i++) {
                if (graph[curr][i] != 0) {  // 把curr的每个邻节点入度减1
                    inDegree[i]--;

                    if (inDegree[i] == 0) { // 如果入度减1后的邻节点入度为0，则入队
                        queue.add(i);
                    }
                }
            }
        }

        // 如果有环，那么在输出全部节点之前，队列就会空
        // 因为总有一个节点出队后，再也找不到入度为1的节点可以入队
        if (ptr == N) {
            return result;
        }

        return null;  // 有环
    }


    public void printGraph() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] g = {
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        Graph graph = new Graph(g, false, false);
        int[] result = graph.topology();
        Print.print1DArray(result);
    }
}
