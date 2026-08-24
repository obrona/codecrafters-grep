package Node;

import java.util.HashSet;

import State.State;

public class PosCharGroupNode extends Node {
    HashSet<Character> charSet = new HashSet<>();

    public PosCharGroupNode(String s) {
        super();
        for (int i = 0; i < s.length(); i++) {
            charSet.add(s.charAt(i));
        }
    }

    @Override
    public boolean match(State state) {
        if (!state.matchPosCharGroup(charSet)) return false;
        
        state.advanceCurrIdx();
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
        }
        state.undo();
        
        return ans;
    }

}
