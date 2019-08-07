package dataStructure;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public long calculate(int[] spots, int[] costs, int N, int K) {
        long minCost = 0;
        for (int warehouse = 0; warehouse < N; warehouse++) {
            long[] currCosts = new long[N - 1];
            int currPtr = 0;
            for (int i = 0; i < N; i++) {
                if (i != warehouse) {
                    currCosts[currPtr] = costs[i] + Math.abs(spots[i] - spots[warehouse]);
                    currPtr++;
                }
            }

            Arrays.sort(currCosts);

            long sum = 0;
            for (int i = 0; i < K; i++) {
                sum += currCosts[i];
            }
            sum += costs[warehouse];

            if (warehouse == 0) {
                minCost = sum;
            } else {
                if (sum < minCost)
                    minCost = sum;
            }

        }

        return minCost;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Solution s = new Solution();
        int T = scanner.nextInt();

        for (int t = 1; t <= T; t++) {
            int K = scanner.nextInt();   // number of stalls
            int N = scanner.nextInt();   // number of spots

            int[] spots = new int[N];
            for (int i = 0; i < N; i++) {
                spots[i] = scanner.nextInt();
            }

            int[] costs = new int[N];
            for (int i = 0; i < N; i++) {
                costs[i] = scanner.nextInt();
            }

            long result = s.calculate(spots, costs, N, K);

            System.out.print("Case #" + t + ": " + result);

            if (t != T) {
                System.out.println();
            }
        }
    }
}
