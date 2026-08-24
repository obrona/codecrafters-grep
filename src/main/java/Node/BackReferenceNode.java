package Node;

import State.State;

public class BackReferenceNode extends Node {
    int id;

    public BackReferenceNode(int id) {
        super();
        this.id = id;
    }

    public boolean match(State state) {
        boolean res = state.matchBackReference(id);
        if (!res) return false;
        
        int len = state.getCaptureLen(id);
        state.advanceCurrIdx(len);
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }
        state.undo();
        return ans;

    }



}
