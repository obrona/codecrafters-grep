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

    private final HashMap<Integer, int[]> captureGroupRange = new HashMap<>();

    public State(String word) {
        this.word = word;
        this.startIdx = 0;
        this.currIdx = startIdx;
    }

    public void advanceCurrIdx() {
        currIdx++;
        undoStack.add(() -> currIdx--);
    }

    public void advanceCurrIdx(int len) {
        currIdx += len;
        undoStack.add(() -> currIdx -= len);
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

    public void startCapture(int id) {
        boolean contains = captureGroupRange.containsKey(id);
        if (!contains) {
            captureGroupRange.put(id, new int[] {currIdx, -1});
            undoStack.add(() -> captureGroupRange.remove(id));
        } else {
            int[] r = captureGroupRange.get(id);
            int prev = r[0];
            r[0] = currIdx;
            undoStack.add(() -> r[0] = prev);
        }
        
    }

    public void endCapture(int id) {
        int[] r = captureGroupRange.get(id);
        int prev = r[1];
        r[1] = currIdx;
        undoStack.add(() -> r[1] = prev);
    }

    public int getCaptureLen(int id) {
        int[] r = captureGroupRange.get(id);
        return r[1] - r[0];
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

    public boolean matchBackReference(int id) {
        int[] r = captureGroupRange.get(id);

        // range can be null as the capture group did not capture anything.
        // eg (a)|b|c\1, if branch 3 is used, c\1, \1 did not capture anything.
        // return false, not right behaviour.
        if (r == null) return false;

        int s = r[0], e = r[1], len = e - s;
        if (word.length() - currIdx < len) return false;
        for (int i = 0; i < len; i++) {
            if (word.charAt(currIdx + i) != word.charAt(s + i)) return false;
        }
        return true;
    }
}
