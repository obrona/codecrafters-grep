package Node;

import State.State;

// StartNode does not match anything
// it is just to store the multiple entries into the NFA
public class StartNode extends Node {

    public StartNode() {
        super();
    }
    
    @Override
    public boolean match(State state) {
        if (state.isEnd()) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }

        if (!ans) {
            state.advanceStartIdx();
            ans = this.match(state);
            state.undo();
        }
        
        return ans;
    }
}
