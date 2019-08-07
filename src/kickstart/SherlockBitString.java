package kickstart;

import java.util.Scanner;

public class SherlockBitString {

    public String solveSmall(int N, int K, int P, int[][] constraints) {
        return "";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt();
        SherlockBitString sbs = new SherlockBitString();
        String[] results = new String[T];
        for (int t = 0; t < T; t++) {
            int N = scanner.nextInt();
            int K = scanner.nextInt();
            int P = scanner.nextInt();

            int[][] constraints = new int[K][3];
            for (int i = 0; i < K; i++) {
                constraints[i][0] = scanner.nextInt();  // Ai
                constraints[i][1] = scanner.nextInt();  // Bi
                constraints[i][2] = scanner.nextInt();  // Ci
            }

            results[t] = sbs.solveSmall(N, K, P, constraints);
        }

        scanner.close();

        for (int t = 1; t < T; t++) {
            System.out.println("Case #" + t + ": " + results[t - 1]);
        }
    }
}
