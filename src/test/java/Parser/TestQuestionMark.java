package Parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;

public class TestQuestionMark {
    @Test
    public void testParseQuestionMark() {
        String pat = "a?";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "b";
        assertTrue(n.match(0, s));

        String s2 = "a";
        assertTrue(n.match(0, s2));
    }
}
