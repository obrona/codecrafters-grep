package Node;

import java.util.HashSet;

public class NegCharGroupNode extends Node {
    HashSet<Character> charSet = new HashSet<>();

    public NegCharGroupNode(String s) {
        super();

        for (int i = 0; i < s.length(); i++) {
            charSet.add(s.charAt(i));
        }
    }

    @Override
    public boolean match(int idx, String s) {
        if (idx == s.length()) return false;
        if (charSet.contains(s.charAt(idx))) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(idx + 1, s);
        }
        return ans;
    }
}
