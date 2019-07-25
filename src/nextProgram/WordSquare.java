package nextProgram;


import java.util.Arrays;
import java.util.Comparator;

public class WordSquare {

    private boolean isWordSquare(String[] words) {
        int k = words.length;

        char[][] letters = new char[k][k];
        for (int i = 0; i < k; i++) {
            if (words[i].length() != k) {
                throw new IllegalArgumentException();
            }

            letters[i] = words[i].toCharArray();
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < i; j++) {
                if (letters[i][j] != letters[j][i]) {
                    return false;
                }
            }
        }

        return true;
    }

    private String[] fillAsSquare(String[] words) {
        return null;
    }

    public String[][] findWordSquares(String[] words) {
        return null;
    }

    public static void main(String[] args) {
        WordSquare ws = new WordSquare();
        String[] test = {"BALL", "AREA", "LEAD", "LADY"};

    }
}
