package Parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Node.Node;
public class ParseTest {
    
    @Test
    public void testParseD() {
        String pattern = "\\d\\d dogs";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();
        
        String s = "11 dogs";
        assertTrue(n.match(0, s));

        String s2 = "1a dogs";
        assertFalse(n.match(0, s2));
    }

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

    @Test
    public void testParsePlus() {
        String pattern = "a+";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s = "SaaS";
        assertTrue(n.match(0, s));

        String s2 = "dog";
        assertFalse(n.match(0, s2));
    }

    @Test
    public void testParseAlt() {
        String pattern = "a|b";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s = "cat";
        assertTrue(n.match(0, s));
    }


}
