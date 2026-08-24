package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;
import State.State;

public class TestStringAnchor {
    @Test
    public void testParseStartString() {
        String pat = "^dog";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "dogs are cool";
        assertTrue(n.match(new State(s)));

        String s2 = "gdogs";
        assertFalse(n.match(new State(s2)));
    }

    @Test
    public void testParseEndString() {
        String pat = "dog$";
        Parse p = new Parse(pat);;
        Node n = p.getNFA();

        String s = "gdog";
        assertTrue(n.match(new State(s)));

        String s2 = "dogs";
        assertFalse(n.match(new State(s2)));
    }
}
