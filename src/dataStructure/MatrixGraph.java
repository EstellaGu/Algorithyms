package dataStructure;

import util.Print;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 目前只是无向无权图
 */
public class MatrixGraph {
    private int[][] graph;   // 没有边的地方用0表示
    private int N;    // N代表节点数
    private boolean weighted;
    private boolean bidirectional;

    public MatrixGraph(int[][] g, boolean weight, boolean directed) {
        graph = g;
        N = g.length;
        weighted = weight;
        bidirectional = directed;
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

    public int[] stackDFS(int start) {
        boolean[] visited  = new boolean[N];
        int[] result = new int[N];
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        int count = 0;
        while (!stack.isEmpty()) {
            int curr = stack.pop();

            if (!visited[curr]) {
                visited[curr] = true;
                result[count] = curr;
                count++;
            }

            for (int i = 0; i < N; i++) {
                if (graph[curr][i] != 0 && !visited[i]) {
                    stack.push(i);
                }
            }
        }

        return result;
    }

    public void recursionDFS(int start) {
        boolean[] visited = new boolean[N];
        recursionDFSUtil(start, visited);
    }

    private void recursionDFSUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");

        for (int i = 0; i < N; i++) {
            if (graph[v][i] != 0 && !visited[i]) {
                recursionDFSUtil(i, visited);
            }
        }
    }

    public int[] BFS(int start) {
        int[] result = new int[N];
        boolean[] visited = new boolean[N];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        result[0] = start;
        int count = 1;
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (int i = 0; i < N; i++) {
                if (graph[curr][i] != 0 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
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
                {0, 0, 1, 1, 0},
                {1, 0, 0, 0, 1},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        MatrixGraph graph = new MatrixGraph(g, false, true);
        int[] result = graph.stackDFS(0);
        Print.print1DArray(result);
    }
}
