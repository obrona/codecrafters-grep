package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;
import State.State;

public class TestAlphaNumeric {
     @Test
    public void testParseW() {
        String pattern = "\\w";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();
        
        String s = "a";
        assertTrue(n.match(new State(s)));

        String s2 = "#";
        assertFalse(n.match(new State(s2)));

        String s3 = "##ab";
        assertTrue(n.match(new State(s3)));
    }
}
