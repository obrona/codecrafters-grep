package Node;

import State.State;

public class DigitNode extends Node {
    public DigitNode() {
        super();
    }

    @Override
    public boolean match(State state) {
        if (!state.matchDigit()) return false;

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
