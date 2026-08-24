package Node;

import State.State

// for the start string anchor ^
public class StartStringNode extends Node {
    public StartStringNode() {
        super();
    }

    @Override
    public boolean match(State state) {
        if (!state.matchStartString()) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }
        
        return ans;
    }
}
