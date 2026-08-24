package Node;

import State.State;

public class AlphaNumericNode extends Node {
    public AlphaNumericNode() {
        super();
    }

    @Override
    public boolean match(State state) {
        if (!state.matchAlphaNumeric()) return false;

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
