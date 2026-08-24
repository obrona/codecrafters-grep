package Node;

import State.State;

// for the end string anchor $
public class EndStringNode extends Node {
    public EndStringNode() {
        super();
    }

    @Override
    public boolean match(State state) {
        if (!state.matchEndString()) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }
        
        return ans;
    }
}
