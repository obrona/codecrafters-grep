package Node;

public class EndNode extends Node {

    public EndNode() {
        super();
    }
    
    @Override
    public boolean match(int idx, String s) {
        return true;
    }
}
