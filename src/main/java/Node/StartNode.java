package Node;

// StartNode does not match anything
// it is just to store the multiple entries into the NFA
public class StartNode extends Node {

    public StartNode() {
        super();
    }
    
    @Override
    public boolean match(State state) {
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }

        // we can start matching at idx + 1 too
        ans |= this.match(idx + 1, s);
        
        return ans;
    }
}
