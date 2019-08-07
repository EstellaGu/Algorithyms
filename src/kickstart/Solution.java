package kickstart;

import java.util.ArrayList;
import java.util.Scanner;

public class Solution {

    public int nextPosition(int prevPosition, char type, int N) {
        int next = prevPosition;
        if (type == 'C') {
            if (prevPosition == N - 1) {
                next = 0;
            } else {
                next = prevPosition + 1;
            }
        } else {
            if (prevPosition == 0) {
                next = N - 1;
            } else {
                next = prevPosition - 1;
            }
        }

        return next;
    }

    public int[] calculate(int N, int G, int M, int[] guestsStarts, char[] guestsType) {
        ArrayList<Integer>[] lastVisit = new  ArrayList[N];

        for (int i = 0; i < N; i++) {
            lastVisit[i] = new ArrayList<>();
        }

        for (int i = 0; i < G; i++) {
            lastVisit[guestsStarts[i]].add(i);
        }

        for (int m = 0; m < M; m++) {
            boolean[] cleared = new boolean[N];
            for (int g = 0; g < G; g++) {
                int nextPos = nextPosition(guestsStarts[g], guestsType[g], N);
                guestsStarts[g] = nextPos;
                if (!cleared[nextPos]) {
                    lastVisit[nextPos].clear();
                    cleared[nextPos] = true;
                }
                lastVisit[nextPos].add(g);
            }
        }


        int[] results = new int[G];
        for (int i = 0; i < N; i++) {
            ArrayList<Integer> last = lastVisit[i];
            for (int g: last) {
                results[g]++;
            }
        }

        return results;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Solution s = new Solution();
        int T = scanner.nextInt();

        for (int t = 1; t <= T; t++) {
            int N = scanner.nextInt();   // number of consulates
            int G = scanner.nextInt();   // number of guests
            int M = scanner.nextInt();   // number of minutes

            int[] guestsStarts = new int[G];
            char[] guestsType = new char[G];
            for (int i = 0; i < G; i++) {
                guestsStarts[i] = scanner.nextInt() - 1;
                guestsType[i] = scanner.next().charAt(0);
            }

            int[] result = s.calculate(N, G, M, guestsStarts, guestsType);

            System.out.print("Case #" + t + ":");

            for (int i = 0; i < result.length; i++) {
                System.out.print(" " + result[i]);
            }

            if (t != T) {
                System.out.println();
            }
        }
    }
}
