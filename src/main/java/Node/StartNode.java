package Node;

// StartNode does not match anything
// it is just to store the multiple entries into the NFA
public class StartNode extends Node {

    public StartNode() {
        super();
    }
    
    @Override
    public boolean match(int idx, String s) {
        if (idx >= s.length()) return false;

        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(idx, s);
        }

        // we can start matching at idx + 1 too
        ans |= this.match(idx + 1, s);
        
        return ans;
    }
}
