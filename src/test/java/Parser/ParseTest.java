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
    public void testParsePosCharGroup() {
        String pattern = "[abc]";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s = "SaaS";
        assertTrue(n.match(0, s));

        String s2 = "dog";
        assertFalse(n.match(0, s2));
    }

    @Test
    public void testParseNegCharGroup() {
        String pattern = "[^abc]";
        Parse parse = new Parse(pattern);
        Node n = parse.getNFA();

        String s2 = "tsac";
        assertTrue(n.match(0, s2));

    }

    @Test
    public void testParseStartString() {
        String pat = "^dog";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "dogs are cool";
        assertTrue(n.match(0, s));

        String s2 = "gdogs";
        assertFalse(n.match(0, s2));
    }

    @Test
    public void testParseEndString() {
        String pat = "dog$";
        Parse p = new Parse(pat);;
        Node n = p.getNFA();

        String s = "gdog";
        assertTrue(n.match(0, s));

        String s2 = "dogs";
        assertFalse(n.match(0, s2));
    }

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

    @Test
    public void testParseMultipleOps() {
        String pat = "(ab)+cd";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "abcd";
        assertTrue(n.match(0, s));

        String s2 = "xyababcd";
        assertTrue(n.match(0, s2));

        String s3 = "xycd";
        assertFalse(n.match(0, s3));
    }

    @Test 
    public void testParseComplex2() {
        String pat = "(ab)+|cd";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "xycd";
        assertTrue(n.match(0, s));

        String s2 = "12ababxy";
        assertTrue(n.match(0, s2));
    }

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

    @Test
    public void testParseNested() {
        String pat = "(((ab)+c)|xy)";
        Parse p = new Parse(pat);
        Node n = p.getNFA();

        String s = "xyz";
        assertTrue(n.match(0, s));

        String s2 = "abx";
        assertFalse(n.match(0, s2));

        String s3 = "abc";
        assertTrue(n.match(0, s3));
    }
}
