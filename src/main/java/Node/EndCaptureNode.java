package Node;

import State.State;

public class EndCaptureNode extends Node {
    int id;

    public EndCaptureNode(int id) {
        super();
        this.id = id;
    }

    public boolean match(State state) {
        state.endCapture(id);
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }
        state.undo();
        return ans;

    }
}
