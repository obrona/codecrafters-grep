package Node;

public class WildcardNode extends Node {
    public WildcardNode() {
        super();
    }

    @Override
    public boolean match(int idx, String s) {
        if (idx == s.length()) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(idx + 1, s);
        }
        return ans;
    }
}
