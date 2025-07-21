package Node;

import java.util.HashSet;

public class PosCharGroupNode extends Node {
    HashSet<Character> charSet = new HashSet<>();

    public PosCharGroupNode(String s) {
        super();
        for (int i = 0; i < s.length(); i++) {
            charSet.add(s.charAt(i));
        }
    }

    @Override
    public boolean match(int idx, String s) {
        if (idx == s.length()) return false;
        
        char c = s.charAt(idx);
        if (!charSet.contains(c)) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(idx + 1, s);
        }
        return ans;
    }

}
