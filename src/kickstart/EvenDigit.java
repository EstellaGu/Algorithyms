package kickstart;

import java.util.Scanner;

/**
 * Kick Start 2018 Round A 第一题
 */
public class EvenDigit {
    public int evendigit(int num) {
        int tmp = num;
        int bits = 0;
        while (tmp != 0) {
            tmp /= 10;
            bits++;
        }

        int[] digits = new int[bits + 1];
        int[] bigger = new int[bits + 1];
        int[] smaller = new int[bits + 1];

        tmp = num;
        for (int i = bits; i >= 1; i--) {
            digits[i] = tmp % 10;
            tmp /= 10;
        }

        int i;
        for (i = 1; i <= bits; i++) {
            bigger[i] = digits[i];
            smaller[i] = digits[i];
            if (digits[i] % 2 == 1) {
                smaller[i] = digits[i] - 1;

                if (digits[i] == 9) {
                    bigger[i] = 0;
                    bigger[i - 1] = bigger[i - 1] + 2;
                } else {
                    bigger[i] = digits[i] + 1;
                }

                break;
            }
        }

        i++;

        for (; i <= bits; i++) {
            smaller[i] = 8;
            bigger[i] = 0;
        }

        int sml = 0;
        int bgr = 0;
        for (int j = 0; j <= bits; j++) {
            sml = sml * 10 + smaller[j];
            bgr = bgr * 10 + bigger[j];
        }



        return Math.min(num - sml, bgr - num);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt();
        for (int t = 1; t <= T; t++) {
            int number = scanner.nextInt();
            EvenDigit ed = new EvenDigit();
            System.out.println("Case #" + t + ": " + ed.evendigit(number));
        }
    }
}
