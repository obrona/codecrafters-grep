package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;
import State.State;

public class TestDigit {
    @Test
    public void testParseD() {
        String pattern = "\\d\\d dogs";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();
        
        String s = "11 dogs";
        assertTrue(n.match(new State(s)));

        String s2 = "1a dogs";
        assertFalse(n.match(new State(s2)));
    }

}
