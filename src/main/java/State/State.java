package State;

import java.util.HashSet;
import java.util.Stack;

public class State {
    private final String word;
    private int startIdx;
    private int currIdx;
    
    private final Stack<Runnable> undoStack;

    public State(String word) {
        this.word = word;
        this.startIdx = 0;
        this.currIdx = startIdx;
        undoStack = new Stack<>();
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
