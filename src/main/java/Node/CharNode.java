package Node;

public class CharNode extends Node {
    char c;

    public CharNode(char c) {
        super();
        this.c = c;
    }

    @Override
    public boolean match(int idx, String s) {
        if (idx == s.length()) return false;
        if (!(s.charAt(idx) == c)) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(idx + 1, s);
        }
        return ans;
    }
}
