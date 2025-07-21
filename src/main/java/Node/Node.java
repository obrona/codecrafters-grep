package Node;

import java.util.ArrayList;

// represents a node in the automata

// if used, represents a Nothing Node
// a node that does no matches
// can be used for Alt (i.e branching)
// Repeats (i.e for +)
// or 0or1 (i.e match pattern or jump to end)
public class Node {
    public ArrayList<Node> nexts = new ArrayList<>();

    public boolean match(int idx, String s) {
        boolean ans = false;
        for (Node n : nexts) {
            ans |= n.match(idx, s);
        }
        return ans;
    }

    public void addNext(Node node) {
        nexts.add(node);
    }


}
