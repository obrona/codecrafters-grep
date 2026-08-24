package Node;

import java.lang.Thread.State;

public class EndNode extends Node {

    public EndNode() {
        super();
    }
    
    @Override
    public boolean match(State state) {
        return true;
    }
}
