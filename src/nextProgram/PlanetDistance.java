package nextProgram;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class PlanetDistance {

    private int[] findLoop(int[] graph, int N) {
        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        boolean[] visited = new boolean[N];
        int vertex = 0;
        stack.push(vertex);
        while (!stack.isEmpty()) { // 当还有未访问过的节点时
            boolean hasUnvisitedNeighbour = false;
            for (int i = 0; i < N; i++) {
                int v1 = Math.max(vertex, i);
                int v2 = Math.min(vertex, i);
                if (graph[get1DIndex(v1, v2)] == 1 && !visited[i]) {
                    // 如果节点i是vertex的邻节点，且这个邻节点没被访问过

                    visited[i] = true; // 访问这个节点
                    stack.push(i);
                    vertex = i;
                    hasUnvisitedNeighbour = true;
                    break;
                } else if (graph[get1DIndex(v1, v2)] == 1 && visited[i]) {
                    // 如果节点i是vertex的邻节点，且这个邻节点被访问过

                    stack.pop();
                    int father = stack.pop();

                    stack.push(father);
                    stack.push(vertex);

                    if (i != father) {
                        // 且这个邻节点不是vertex被之前被访问的那个节点（就是两个节点不成环）
                        // 成环
                        ArrayList<Integer> arr = new ArrayList<>();

                        while (vertex != i) {
                            vertex = stack.pop();
                            arr.add(vertex);
                        }

                        int[] results = new int[arr.size()];
                        for (int j = 0; j < arr.size(); j++) {
                            results[j] = arr.get(j);
                        }

                        return results;
                    }
                }
            }

            if (!hasUnvisitedNeighbour) {
                stack.pop();
                vertex = stack.peek();
            }
        }

        return null;
    }

    public int[] getMinimum(int[] graph, int N) {
        int[] results = new int[N];

        for (int k = 0; k < N; k++) {
            results[k] = Integer.MAX_VALUE;
        }

        int[] loop = findLoop(graph, N);

        for (int i = 0; i < loop.length; i++){
            // 求出环上每一个点到其他点的最短路径

            Queue<Integer> queue = new LinkedList<>();

            int[] dist = new int[N * (N + 1) / 2];
            int vertex = loop[i];
            queue.offer(vertex);
            for (int k = 0; k < N; k++) {
                dist[k] = -1;
            }
            dist[vertex] = 0;
            while (!queue.isEmpty()) {
                vertex = queue.poll();

                for (int j = 0; j < N; j++) {
                    int v1 = Math.max(vertex, j);
                    int v2 = Math.min(vertex, j);
                    if (graph[get1DIndex(v1, v2)] == 1 && dist[j] == -1) {
                        // j是vertex的邻接点且没有被访问过

                        dist[j] = dist[vertex] + 1;
                        queue.offer(j);
                    }
                }
            }

            for (int k = 0; k < N; k++) {
                int x = Math.max(loop[i], k);
                int y = Math.min(loop[i], k);

                if (results[k] > dist[k]) {
                    results[k] = dist[k];
                }
            }

            results[loop[i]] = 0;

        }

        return results;
    }

    private static int get1DIndex(int x, int y) {
        return x * (x + 1) / 2 + y;
    }

    private static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PlanetDistance pd = new PlanetDistance();
        int T = scanner.nextInt();

        for (int t = 0; t < T; t++) {
            int N = scanner.nextInt();   // 共有N个节点

            int[] graph = new int[N * (N + 1) / 2];
            for (int n = 0; n < N; n++){
                int f = scanner.nextInt();
                int s = scanner.nextInt();

                int xi = Math.max(f, s);
                int yi = Math.min(f, s);

                graph[get1DIndex(xi - 1, yi - 1)] = 1;
            }


            int[] result = pd.getMinimum(graph, N);
            System.out.print("Case #" + t + ": ");
            printArr(result);
        }
    }
}
