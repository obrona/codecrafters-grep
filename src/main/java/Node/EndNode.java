package Node;

import State.State;

public class EndNode extends Node {

    public EndNode() {
        super();
    }
    
    @Override
    public boolean match(State state) {
        return true;
    }
}
