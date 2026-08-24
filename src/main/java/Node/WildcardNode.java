package Node;

import State.State;

public class WildcardNode extends Node {
    public WildcardNode() {
        super();
    }

    @Override
    public boolean match(State state) {
        if (!state.matchWildcard()) return false;

        state.advanceCurrIdx();
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if(ans) break;
        }
        state.undo();
        
        return ans;
    }
}
