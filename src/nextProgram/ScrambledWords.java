package nextProgram;

import java.util.Scanner;
import util.Color;

/**
 * Kick Start 2018 Round A 第三题
 */
public class ScrambledWords {
    private boolean[] isScrambled;

    public int count(String S, int sLength, String[] dictionary, int dicLength) {
        if (sLength == 0) {
            return 0;
        }

        int count = 0;
        isScrambled = new boolean[dicLength];
        for (int i = 0; i < dicLength; i++) {
            int currLength = dictionary[i].length();
            if (currLength > 0 && currLength <= sLength) {

                int[] dictFrequency = frequency(dictionary[i]);
                int[] lastFrequency = new int[26];

//                System.out.println("dictionary[" + i + "]: " + dictionary[i]);
//                for (int k = 0; k < 26; k++) {
//                    System.out.print(dictFrequency[k] + " ");
//                }
//                System.out.println();

                for (int j = 0; j < sLength - currLength + 1; j++) {
                    String toCompare = S.substring(j, j + currLength);
//                    System.out.println("toCompare: " + toCompare);
                    if (j == 0) {
                        lastFrequency = frequency(toCompare);
                    } else {
                        lastFrequency[S.charAt(j - 1) - 97]--;
                        lastFrequency[S.charAt(j + currLength - 1) - 97]++;
                    }
//                    for (int k = 0; k < 26; k++) {
//                        System.out.print(lastFrequency[k] + " ");
//                    }
//                    System.out.println();

                    if (toCompare.charAt(0) == dictionary[i].charAt(0)
                            && toCompare.charAt(currLength - 1) == dictionary[i].charAt(currLength - 1)) {

                        boolean isEqual = isEqual(dictFrequency, lastFrequency);
                        if (isEqual) {
                            count++;
//                            System.out.println(dictionary[i]);
                            isScrambled[i] = true;
                            break;
                        }
                    }
                }
            }
        }

        return count;
    }

    private int[] frequency(String s) {
        int[] count = new int[26];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 97]++;
        }

        return count;
    }

    private boolean isEqual(int[] fre1, int[] fre2) {

        for (int i = 0; i < fre1.length; i++) {
            if (fre1[i] != fre2[i]) {
                return false;
            }
        }

        return true;
    }

    private int ord(char x) {
        return (int) x;
    }

    private char chara(int x) {
        return (char)(x);
    }

    private String getS(String s1, String s2, int N, int A, int B, int C, int D) {
        StringBuilder S = new StringBuilder();
        S.append(s1);
        S.append(s2);
        int[] x = new int[N];
        x[0] = ord(s1.charAt(0));
        x[1] = ord(s2.charAt(0));
        for (int i = 2; i < N; i++) {
            int xi = (A * x[i - 1] + B * x[i - 2] + C) % D;
            char si = chara(xi % 26 + 97);
            x[i] = xi;
            S.append(si);
        }

        return new String(S);
    }

    private int randomInt(int low, int high) {
        if (low >= high) {
            throw new IllegalArgumentException();
        }
        return (int) (Math.random() * (high - low) + low);
    }

    private char randomChar() {
        int ran = randomInt(0, 26);
        return chara(ran + 97);
    }

    private String getScramble(String str) {
        if (str.length() <=3) {
            return str;
        }

        char[] mid = str.substring(1, str.length() - 1).toCharArray();
        boolean[] added = new boolean[mid.length];
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < mid.length) {
            int ptr = randomInt(0, mid.length);
            if (!added[ptr]) {
                sb.append(mid[ptr]);
                added[ptr] = true;
                i++;
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(str.charAt(0));
        result.append(sb);
        result.append(str.charAt(str.length() - 1));

        return result.toString();
    }

    private void printFailInfo(int count, int test, String S, String[] dict, int L) {
        System.out.println("==> failed");
        System.out.println("expected: " + count + ", actual: " + test);
        System.out.println("S ------------------------------------------------");
        System.out.println(S);
        System.out.println("Expected Dictionary ------------------------------------------------");
        int k = 0;
        for (; k < count; k++) {
            System.out.println(Color.ANSI_RED + k + ": " + dict[k] + Color.ANSI_RESET);
        }

        for (; k < L; k++) {
            System.out.println(k + ": " + dict[k]);
        }

        System.out.println("Actual Dictionary ------------------------------------------------");
        for (int i = 0; i < L; i++) {
            if (isScrambled[i]) {
                System.out.println(Color.ANSI_RED + i + ": " + dict[i] + Color.ANSI_RESET);
            } else {
                System.out.println(i + ": " + dict[i]);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt();   // test case number

        int[] counts = new int[T];
        for (int test = 0; test < T; test++) {
            int L = scanner.nextInt();   // dictionary words number
            String[] dictionary = new String[L];    // dictionary
            for (int i = 0; i < L; i++) {
                dictionary[i] = scanner.next();
            }

            String s1 = scanner.next();   // the first char of S
            String s2 = scanner.next();   // the second char of S
            int N = scanner.nextInt();    // the length of S
            int A = scanner.nextInt();
            int B = scanner.nextInt();
            int C = scanner.nextInt();
            int D = scanner.nextInt();

            ScrambledWords sw = new ScrambledWords();
            String S = sw.getS(s1, s2, N, A, B, C, D);
            System.out.println(S);
            counts[test] = sw.count(S, N, dictionary, L);

        }

        for (int i = 0; i < T; i++) {
            System.out.println("Case #" + (i + 1) + ": " + counts[i]);
        }

        scanner.close();


        ScrambledWords sw = new ScrambledWords();

        for (int t = 1; t <= 100; t++) {
            int N = sw.randomInt(2, 100);     // S的长度
            int A = sw.randomInt(0, 100);
            int B = sw.randomInt(0, 100);
            int C = sw.randomInt(0, 100);
            int D = sw.randomInt(1, 100);
            String s1 = sw.randomChar() + "";
            String s2 = sw.randomChar() + "";
            String S = "";
            try {
                S = sw.getS(s1, s2, N, A, B, C, D);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("s1: " + s1);
                System.out.println("s2: " + s2);
                System.out.println("N: " + N);
                System.out.println("A: " + A);
                System.out.println("B: " + B);
                System.out.println("C: " + C);
                System.out.println("D: " + D);
            }
            int L = sw.randomInt(0, 100);      // 字典里的单词数
            String[] dict = new String[L];
            int count = sw.randomInt(0, L + 1);   // 这L个单词里有count个是满足scrambled条件的
            int i = 0;
            for (; i < count; i++) {
                int low = sw.randomInt(0, N - 1);
                int high = sw.randomInt(low + 2, N + 1);
                String sub = S.substring(low, high);
                String scramble = sw.getScramble(sub);
                dict[i] = scramble;
            }

            // 如何保证随机产生的字符串不是scrambled word?
            for (; i < L; i++) {
                int len = sw.randomInt(101, 105);
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < len; j++) {
                    sb.append(sw.randomChar());
                }

                dict[i] = sb.toString();
            }

            System.out.println("Test Case #" + t + ": ");
            int test = 0;
            try {
                test = sw.count(S, N, dict, L);

                if (test == count) {
                    System.out.println("==> passed");
                } else {
                    sw.printFailInfo(count, test, S, dict, L);
                }
            } catch (Exception e) {
                System.out.println(e);
                sw.printFailInfo(count, test, S, dict, L);
            }

            System.out.println("=================================================================================");
            System.out.println();
        }

    }
}