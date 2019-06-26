package nextProgram;

public class LongestSubsequence {
    /**
     * S.length() = n
     * T = O(n)
     */
    private boolean scanS(String S, String W) {
        int sLen = S.length();
        int wLen = W.length();

        if (wLen > sLen) {
            return false;
        }

        int sPtr = 0;


        for (int i = 0; i < wLen; i++) {
            char wi = W.charAt(i);
            while (sPtr < sLen && S.charAt(sPtr) != wi) {
                sPtr++;
            }

            if (sPtr >= sLen) {
                return false;
            }

            sPtr++;
        }

        return true;
    }

    /**
     * S.length() = n
     * W.length() = m
     * T = O(nm)
     */
    private boolean dp(String S, String W) {
        int sLen = S.length();
        int wLen = W.length();

        if (wLen > sLen) {
            return false;
        }

        boolean[][] dp = new boolean[wLen][sLen];

        dp[0][0] = S.charAt(0) == W.charAt(0);
        for (int j = 1; j < sLen; j++) {
            dp[0][j] = dp[0][0];
        }

        for (int i = 1; i < wLen; i++) {
            for (int j = 1; j < sLen; j++) {
                dp[i][j] = dp[i - 1][j - 1] && S.charAt(j) == W.charAt(i);

                if (dp[i][j]) {
                    for (int k = j + 1; k < sLen; k++) {
                        dp[i][k] = true;
                    }

                    break;
                }
            }
        }

        return dp[wLen - 1][sLen - 1];
    }

    public String findLongestSubSequence(String S, String[] D) {
        if (S == null || S.length() == 0) {
            return null;
        }

        if (D == null || D.length == 0) {
            return null;
        }

        String longest = null;
        int length = 0;
        for (String w : D) {
            if (scanS(S, w) && w.length() > length) {
                longest = w;
                length = w.length();
            }
        }

        return longest;
    }

    public static void main(String[] args) {
        LongestSubsequence ls = new LongestSubsequence();
        String S = "abppplee";
        String[] D = {"able", "ale", "apple", "bale", "kangaroo"};
    }
}
