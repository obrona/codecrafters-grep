package Node;

import java.util.ArrayList;
import State.State;

// represents a node in the automata

// if used, represents a Nothing Node
// a node that does no matches
// can be used for Alt (i.e branching)
// Repeats (i.e for +)
// or 0or1 (i.e match pattern or jump to end)
public class Node {
    public ArrayList<Node> nexts = new ArrayList<>();

    public boolean match(State state) {
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(state);
            if (ans) break;
        }
        return ans;
    }

    public void addNext(Node node) {
        nexts.add(node);
    }


}
