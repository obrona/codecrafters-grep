package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;
import State.State;

public class TestCharGroup {
    @Test
    public void testParsePosCharGroup() {
        String pattern = "[abc]";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s = "SaaS";
        assertTrue(n.match(new State(s)));

        String s2 = "dog";
        assertFalse(n.match(new State(s2)));
    }

    @Test
    public void testParseNegCharGroup() {
        String pattern = "[^abc]";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s2 = "tsac";
        assertTrue(n.match(new State(s2)));

    }
}
