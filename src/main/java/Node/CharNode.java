package Node;

import State.State;

public class CharNode extends Node {
    char c;

    public CharNode(char c) {
        super();
        this.c = c;
    }

    @Override
    public boolean match(State state) {
        if (!state.matchChar(c)) return false;

        state.advanceCurrIdx();
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }
        state.undo();
        
        return ans;
    }
}
