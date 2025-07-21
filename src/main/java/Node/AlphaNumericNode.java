package Node;

public class AlphaNumericNode extends Node {
    public AlphaNumericNode() {
        super();
    }

    @Override
    public boolean match(int idx, String s) {
        if (idx == s.length()) return false;
       
        char c = s.charAt(idx);
        if (!(Character.isLetterOrDigit(c) || c == '_')) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(idx + 1, s);
        }

        return ans;
    }
}
