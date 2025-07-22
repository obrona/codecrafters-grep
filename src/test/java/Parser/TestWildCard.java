package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;

public class TestWildCard {
    @Test
    public void testWildCard() {
        Node n = new Parse("d.g").getNFA();

        String s = "dog";
        assertTrue(n.match(0, s));

        String s2 = "cat";
        assertFalse(n.match(0, s2));
    }
}
