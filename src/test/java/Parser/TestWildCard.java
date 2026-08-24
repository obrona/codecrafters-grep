package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;
import State.State;

public class TestWildCard {
    @Test
    public void testWildCard() {
        Node n = new Parse("d.g").getNFA();

        String s = "dog";
        assertTrue(n.match(new State(s)));

        String s2 = "cat";
        assertFalse(n.match(new State(s2)));
    }
}
