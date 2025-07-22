package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;

public class TestAlphaNumeric {
     @Test
    public void testParseW() {
        String pattern = "\\w";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();
        
        String s = "a";
        assertTrue(n.match(0, s));

        String s2 = "#";
        assertFalse(n.match(0, s2));

        String s3 = "##ab";
        assertTrue(n.match(0, s3));
    }
}
