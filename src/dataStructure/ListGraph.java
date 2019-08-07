package dataStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ListGraph {
    ArrayList<Integer>[] graph;
    int V;

    public ListGraph(int[][] edges, int numOfVertices) {
        V = numOfVertices;
        graph = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            graph[src].add(dest);
            graph[dest].add(src);
        }
    }

    private ArrayList<Integer> findLoop(ArrayList<Integer>[] graph, int N) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[N];
        boolean[] inStack = new boolean[N];
        int[] tree = new int[N];

        stack.add(0);
        inStack[0] = true;
        tree[0] = -1;
        while (!stack.isEmpty()) {
            int curr = stack.pop();

            visited[curr] = true;

            ArrayList<Integer> adj = graph[curr];
            for (int v: adj) {
                if (!visited[v] && !inStack[v]) {
                    stack.push(v);
                    inStack[v] = true;
                    tree[v] = curr;
                } else if (visited[v] && v != tree[curr]) {
                    ArrayList<Integer> loop = new ArrayList<>();
                    while (v != curr) {
                        loop.add(v);
                        loop.add(curr);
                        if (tree[v] != -1) v = tree[v];
                        if (tree[curr] != -1) curr = tree[curr];
                    }
                    loop.add(v);

                    return loop;
                }
            }
        }

        return new ArrayList<>();
    }

    private ArrayList<Integer> findLoop(ArrayList<Integer>[] graph, int N, int[] degrees) {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i] == 1) {
                queue.add(i);
                degrees[i]--;
            }
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();

            ArrayList<Integer> adj = graph[curr];
            for (int v: adj) {
                if (degrees[v] > 0)
                    degrees[v]--;

                if (degrees[v] == 1) {
                    queue.add(v);
                    degrees[v]--;
                }
            }
        }

        ArrayList<Integer> loop = new ArrayList<>();
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i] > 0)
                loop.add(i);
        }

        return loop;
    }

    public void printGraph() {
        for (int i = 0; i < V; i++) {
            ArrayList<Integer> vertices = graph[i];

            System.out.print(i);

            for (int v: vertices) {
                System.out.print(" -> " + v);
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] test = {
                {0, 1},
                {0, 4},
                {1, 2},
                {1, 3},
                {1, 4},
                {2, 3},
                {3, 4}
        };
        ListGraph graph = new ListGraph(test, 5);
        graph.printGraph();
    }
}
