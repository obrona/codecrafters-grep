package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;
import State.State;

public class TestPlus {
     @Test
    public void testParsePlus() {
        String pattern = "a+";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s = "SaaS";
        assertTrue(n.match(new State(s)));

        String s2 = "dog";
        assertFalse(n.match(new State(s2)));
    }
}
