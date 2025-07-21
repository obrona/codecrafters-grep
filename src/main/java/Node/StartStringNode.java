package Node;

// for the start string anchor ^
public class StartStringNode extends Node {
    public StartStringNode() {
        super();
    }

    @Override
    public boolean match(int idx, String s) {
        if (idx != 0) return false;

        boolean ans = false;
        
        for (Node n : nexts) {
            ans |= n.match(idx, s);
        }
        
        return ans;
    }
}
