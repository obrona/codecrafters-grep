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

    void advanceCurrIdx() {
        currIdx++;
        undoStack.add(() -> currIdx--);
    }

    void advanceStartIdx() {
        startIdx++;
        undoStack.add(() -> startIdx--);
    }

    boolean isEnd() {
        return currIdx == word.length();
    }


    void undo() {
        if (undoStack.isEmpty()) return;
        undoStack.pop().run();
    }

    boolean matchAlphaNumeric() {
        if (currIdx == word.length()) return false;

        char c = word.charAt(currIdx);
        if (!Character.isLetterOrDigit(c) && c != '_') return false;

        advanceCurrIdx();
        return true;
    }

    boolean matchChar(char c) {
        if (currIdx == word.length()) return false;
        if (word.charAt(currIdx) != c) return false;
        advanceCurrIdx();
        return true;
    }

    boolean matchDigit() {
        if (currIdx == word.length()) return false;
        if (!Character.isDigit(word.charAt(currIdx))) return false;
        advanceCurrIdx();
        return true;
    }

    boolean matchPosCharGroup(HashSet<Character> group) {
        if (currIdx == word.length()) return false;
        if (!group.contains(word.charAt(currIdx))) return false;
        advanceCurrIdx();
        return true;
    }

    boolean matchNegCharGroup(HashSet<Character> group) {
        if (currIdx == word.length()) return false;
        if (group.contains(word.charAt(currIdx))) return false;
        advanceCurrIdx();
        return true;
    }

    boolean matchWildcard() {
        if (currIdx == word.length()) return false;
        advanceCurrIdx();
        return true;
    }

    boolean matchStartString() {
        return currIdx == 0;
    }

    boolean matchEndString() {
        return currIdx == word.length();
    }

   


}
