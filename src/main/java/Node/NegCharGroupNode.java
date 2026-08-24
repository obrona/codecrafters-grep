package Node;

import State.State;
import java.util.HashSet;

public class NegCharGroupNode extends Node {
    HashSet<Character> charSet = new HashSet<>();

    public NegCharGroupNode(String s) {
        super();

        for (int i = 0; i < s.length(); i++) {
            charSet.add(s.charAt(i));
        }
    }

    @Override
    public boolean match(State state) {
        if (!state.matchNegCharGroup(charSet)) return false;

        state.advanceCurrIdx();
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
        }
        state.undo();

        return ans;
    }
}
