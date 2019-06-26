package dataStructure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 这个字典树只接受字符串数量和每个单词的字符数一样的字符串数组
 */
public class Trie {
    private TrieNode root;

    public Trie(String[] words) {
        int k = words.length;

        char[][] letters = new char[k][k];
        for (int i = 0; i < k; i++) {
            if (words[i].length() != k) {
                throw new IllegalArgumentException();
            }

            letters[i] = words[i].toCharArray();
        }

        root = new TrieNode('0', false);
        TrieNode[] pointers = new TrieNode[k];
        for (int j = 0; j < k; j++) {
            root.setChild(letters[j][0], k == 1);
            pointers[j] = root.getChild(letters[j][0]);
        }

        for (int i = 1; i < k; i ++) {    // 第i个字母
            for (int j = 0; j < k; j++) {  // 第j个单词
                // 把第j个单词的第i个字母加入树
                pointers[j].setChild(letters[j][i], i == k - 1);
                pointers[j] = pointers[j].getChild(letters[j][i]);
            }
        }

        pointers = null;
    }

    private class TrieNode {
        private char val;
        private TrieNode[] children;
        private boolean isNode;
        private int childrenCount;

        public TrieNode(char c, boolean node) {
            val = c;
            children = new TrieNode[26];
            isNode = node;
            childrenCount = 0;
        }

        public void setChild(char child, boolean node) {
            int index = child - 65;
            if (children[index] == null) {
                children[index] = new TrieNode(child, node);
                childrenCount++;
            }
        }

        public TrieNode getChild(char child) {
            return children[child - 65];
        }

        public String toString() {
            return val + "";
        }

        public TrieNode[] getChildren() {
            if (childrenCount == 0) {
                return null;
            }

            TrieNode[] c = new TrieNode[childrenCount];
            int cptr = 0;
            for (int i = 0; i < 26; i++) {
                if (children[i] != null) {
                    c[cptr] = children[i];
                    cptr++;
                }
            }

            return c;
        }

        public void printWholeTree() {
            System.out.println(val);

            ArrayList<TrieNode> childs = new ArrayList<>();
            for (int i = 0; i < 26; i++) {
                if (children[i] != null) {
                    childs.add(children[i]);
                }
            }

            while (!childs.isEmpty()) {
                ArrayList<TrieNode> tmp = new ArrayList<>();

                for (TrieNode t: childs) {
                    System.out.print(t + " ");

                    TrieNode[] c = t.getChildren();

                    if (c != null) {
                        Collections.addAll(tmp, c);
                    }
                }

                System.out.println();
                childs = tmp;
            }
            System.out.println();
        }

    }

    public void printTree() {
        root.printWholeTree();
    }

    public static void main(String[] args) {

    }
}
