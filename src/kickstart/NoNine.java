package kickstart;

import java.util.Scanner;

/**
 * Kick Start 2018 Round B 第一题
 */
public class NoNine {
    private boolean isNoNine(int x) {
        if (x % 9 == 0) {
            return false;
        }

        x = Math.abs(x);
        while (x != 0) {
            if (x % 10 == 9) {
                return false;
            }

            x /= 10;
        }

        return true;
    }

    // 找出0～x之间的NoNine数的个数
    private long count(long x) {
        char[] digits = String.valueOf(x).toCharArray();
        int len = digits.length;

        long count = 0;
        for (int i = 0; i < len; i++) {
            int digit = digits[i] - '0';

            // 比如，第一位是5
            // 那么比它小的数第一位可以取0, 1, 2, 3, 4，中间可以是012345678，最后一位可以是加上它后不是9的倍数的那个数
            // 到第二轮循环的时候，其实是默认第一位是5，然后整个数要比x小，第二位就得比x的第二位小，比如第二位是6，就可以取0, 1, 2, 3, 4, 5
            // 即使是最高位也可以为0，这样所有的位数都考虑进去了
            if (i < len - 1) {
                count += digit * Math.pow(9, len - 2 - i) * 8;
            } else {
                for (long j = x - x % 10; j <= x; j++) {
                    if (j % 9 != 0) {
                        count++;
                    }
                }
            }

        }

        return count;
    }

    public long calculateTurns(long F, long L) {
        return count(L) - count(F) + 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt();

        long[] results = new long[T];
        NoNine nn = new NoNine();
        for (int t = 0; t < T; t++) {
            long F = scanner.nextInt();
            long L = scanner.nextInt();

            results[t] = nn.calculateTurns(F, L);
        }

        for (int t = 1; t <= T; t++) {
            System.out.println("Case #" + t + ": " + results[t - 1]);
        }
    }
}
