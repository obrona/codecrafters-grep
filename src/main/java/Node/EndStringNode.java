package Node;

// for the end string anchor $
public class EndStringNode extends Node {
    public EndStringNode() {
        super();
    }

    @Override
    public boolean match(int idx, String s) {
        if (idx != s.length()) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(idx, s);
        }
        return ans;
    }
}
