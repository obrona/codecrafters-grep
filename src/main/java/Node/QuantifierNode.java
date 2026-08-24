package Node;

import State.State;

public class QuantifierNode extends Node {
    int n;
    int m;

    // cannot store a eplison node, must go to a node that will advance currIdx.
    Node repeat = null;

    public QuantifierNode(int n, int m) {
        this.n = n;
        this.m = m;
    }

    public QuantifierNode(int n) {
        this(n, 2000000000);
    }

    public void addRepeat(Node node) {
        this.repeat = node;
    }

    @Override
    public boolean match(State state) {
        int currCnt = state.getQuantifierNodeCnt(this);

        if (currCnt >= n) {
            boolean ans = false;
            for (Node n : nexts) {
                ans |= n.match(state);
                if (ans) return true;
            }
        }

        if (currCnt < m) {
            state.incrementQuantifierNode(this);
            boolean ans = repeat.match(state);
            state.undo();
            if (ans) return true;
        }

        return false;
        
    }
}
