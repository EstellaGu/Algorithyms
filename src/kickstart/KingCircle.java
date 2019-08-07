package kickstart;

import java.util.Scanner;

public class KingCircle {

    private int calculatePos(int V, int H, int coe1, int coe2, int coe3, int M) {
        return (coe1 * V + coe2 * H + coe3) % M;
    }

    // 检查点(x_, y_)是否严格在矩形里面
    private boolean isInSide (int x, int y, int width, int height, int x_, int y_) {
        int xDist = x_ - x;
        int yDist = y_ - y;

        return xDist > 0 && xDist < width && yDist > 0 && yDist < height;
    }

    public int count(int N, int V1, int H1, int A, int B, int C, int D, int E, int F, int M) {
        int[][] cafes = new int[N][2];
        cafes[0][0] = V1;
        cafes[0][1] = H1;

        for (int i = 1; i < N; i++) {
            cafes[i][0] = calculatePos(cafes[i - 1][0], cafes[i - 1][1], A, B, C, M);
            cafes[i][1] = calculatePos(cafes[i - 1][0], cafes[i - 1][1], D, E, F, M);
        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    int[] point1 = cafes[i];
                    int[] point2 = cafes[j];

                    int width = Math.abs(point1[0] - point2[0]);
                    int height = Math.abs(point1[1] - point2[1]);
                    int x = Math.min(point1[0], point2[0]);
                    int y = Math.min(point1[1], point2[1]);

                    for (int k = 0; k < N; k++) {
                        if (k != i && k != j && !isInSide(x, y, width, height, cafes[k][0], cafes[k][1])) {
                            result++;
                        }
                    }
                }
            }
        }

        return result / 3;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        KingCircle kc = new KingCircle();
        int T = scanner.nextInt();
        int[] results = new int[T];
        for (int t = 0; t < T; t++) {
            int N = scanner.nextInt();
            int V1 = scanner.nextInt();
            int H1 = scanner.nextInt();
            int A = scanner.nextInt();
            int B = scanner.nextInt();
            int C = scanner.nextInt();
            int D = scanner.nextInt();
            int E = scanner.nextInt();
            int F = scanner.nextInt();
            int M = scanner.nextInt();

            results[t] = kc.count(N, V1, H1, A, B, C, D, E, F, M);
        }

        scanner.close();

        for (int t = 1; t <= T; t++) {
            System.out.println("Case #" + t + ": " + results[t - 1]);
        }
    }
}
