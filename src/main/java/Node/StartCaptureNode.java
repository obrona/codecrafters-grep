package Node;

import State.State;

public class StartCaptureNode extends Node {
    int id;

    public StartCaptureNode(int id) {
        super();
        this.id = id;
    }

    public boolean match(State state) {
        state.startCapture(id);
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }
        state.undo();
        return ans;
    }
}
