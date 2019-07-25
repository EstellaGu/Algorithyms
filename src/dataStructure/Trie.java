package dataStructure;

import java.util.ArrayList;
import java.util.Collections;


class Trie {
    private TrieNode root;

    private class TrieNode{
        private char val;
        private TrieNode[] children;
        private boolean isWord;

        public TrieNode (char c) {
            val = c;
            children = new TrieNode[26];

            for (int i = 0; i < 26; i++) {
                children[i] = null;
            }
        }

        public void addChild (char c) {
            children[c - 'a'] = new TrieNode(c);
        }

        public TrieNode getChild (char c) {
            return children[c - 'a'];
        }

    }

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode('0');
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode ptr = root;

        for (int i = 0; i < word.length(); i++) {
            char curr = word.charAt(i);
            if (ptr.getChild(curr) == null) {
                ptr.addChild(curr);
            }

            ptr = ptr.getChild(curr);

            if (i == word.length() - 1) {
                ptr.isWord = true;
            }
        }
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        for (int i = 0; i < word.length(); i++) {

        }

        return false;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return false;
    }
}
