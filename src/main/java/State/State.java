package State;

import Node.QuantifierNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class State {
    private final String word;
    private int startIdx;
    private int currIdx;
    
    private final Stack<Runnable> undoStack = new Stack<>();

    private final HashMap<QuantifierNode, Integer> quantiferNodeStore = new HashMap<>();

    public State(String word) {
        this.word = word;
        this.startIdx = 0;
        this.currIdx = startIdx;
    }

    public void advanceCurrIdx() {
        currIdx++;
        undoStack.add(() -> currIdx--);
    }

    public void advanceStartIdx() {
        startIdx++;
        int temp = currIdx;
        currIdx = startIdx;
        undoStack.add(() -> {
            startIdx--;
            currIdx = temp;
        });
    }

    public void incrementQuantifierNode(QuantifierNode node) {
       int cnt = quantiferNodeStore.getOrDefault(node, 0);
       quantiferNodeStore.put(node, cnt + 1);
       undoStack.add(() -> quantiferNodeStore.put(node, cnt));
    }

    public int getQuantifierNodeCnt(QuantifierNode node) {
        return quantiferNodeStore.getOrDefault(node, 0);
    }

    public boolean isEnd() {
        return currIdx == word.length();
    }


    public void undo() {
        if (undoStack.isEmpty()) return;
        undoStack.pop().run();
    }

    public boolean matchAlphaNumeric() {
        if (currIdx == word.length()) return false;

        char c = word.charAt(currIdx);
        if (!Character.isLetterOrDigit(c) && c != '_') return false;

        return true;
    }

    public boolean matchChar(char c) {
        if (currIdx == word.length()) return false;
        if (word.charAt(currIdx) != c) return false;
        return true;
    }

    public boolean matchDigit() {
        if (currIdx == word.length()) return false;
        if (!Character.isDigit(word.charAt(currIdx))) return false;
        return true;
    }

    public boolean matchPosCharGroup(HashSet<Character> group) {
        if (currIdx == word.length()) return false;
        if (!group.contains(word.charAt(currIdx))) return false;
        return true;
    }

    public boolean matchNegCharGroup(HashSet<Character> group) {
        if (currIdx == word.length()) return false;
        if (group.contains(word.charAt(currIdx))) return false;
        return true;
    }

    public boolean matchWildcard() {
        if (currIdx == word.length()) return false;
        return true;
    }

    public boolean matchStartString() {
        return currIdx == 0;
    }

    public boolean matchEndString() {
        return currIdx == word.length();
    }
}
