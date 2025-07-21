package Parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Node.Node;

public class ComplexExprTest {
    @Test
    public void test1() {
        String pat = "[xy]+";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "xyxyxy";
        assertTrue(n.match(0, s));

        String s2 = "abcd";
        assertFalse(n.match(0, s2));
    }

    @Test
    public void test2() {
        String pat = "ab|((cd)+e?[fg])";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "cdcdefg";
        assertTrue(n.match(0, s));

        String s2 = "123axfg";
        assertFalse(n.match(0, s2));

       
    }
}
