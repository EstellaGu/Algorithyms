package kickstart;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

/*
* KickStart 2018 Round C Problem 1
* 全通过
* */
public class PlanetDistance {

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

    public int[] getMinimum(ArrayList<Integer>[] graph, int N, int[] degrees) {
        int[] dist = new int[N];
        ArrayList<Integer> loop = findLoop(graph, N, degrees);

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[N];
        for (int v: loop){
            queue.add(v);
            visited[v] = true;
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();

            ArrayList<Integer> adj = graph[curr];
            for (int v: adj) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(v);
                    dist[v] = dist[curr] + 1;
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PlanetDistance pd = new PlanetDistance();
        int T = scanner.nextInt();

        for (int t = 0; t < T; t++) {
            int N = scanner.nextInt();

            ArrayList<Integer>[] graph = new ArrayList[N];
            int[] degrees = new int[N];
            for (int i = 0; i < N; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int n = 0; n < N; n++){
                int src = scanner.nextInt() - 1;
                int dest = scanner.nextInt() - 1;
                graph[src].add(dest);
                graph[dest].add(src);
                degrees[src]++;
                degrees[dest]++;
            }

            int[] result = pd.getMinimum(graph, N, degrees);
            System.out.print("Case #" + (t + 1) + ":");
            for (int i = 0; i < result.length; i++) {
                System.out.print(" " + result[i]);
            }

            if (t != T - 1) {
                System.out.println();
            }
        }
    }
}
