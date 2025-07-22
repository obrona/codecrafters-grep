package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;

public class TestAlt {
     @Test
    public void testParseAlt() {
        String pattern = "a|b";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s = "cat";
        assertTrue(n.match(0, s));

        String s2 = "dog";
        assertFalse(n.match(0, s2));
    }

    @Test
    public void testParseComplex() {
        String pattern = "cat|dog";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s = "12cat34";
        assertTrue(n.match(0, s));

        String s2 = "dogcat";
        assertTrue(n.match(0, s2));

        String s3 = "squid game";
        assertFalse(n.match(0, s3));
    }

    @Test
    public void testParseBrackets() {
        String pat = "(a|b)";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "cat";
        assertTrue(n.match(0, s));

        String s2 = "dog";
        assertFalse(n.match(0, s2));
    }
}
