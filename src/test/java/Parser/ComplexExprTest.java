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

    @Test
    public void testParseMultipleAlts() {
        Node n = new Parse("ab|(c(xy)+)|ef").getNFA();

        String s = "xycd";
        assertFalse(n.match(0,s));
    }

    @Test
    public void testParseNested() {
        Node n = new Parse("ab\\d|(c(xy)+)|(e?fg)").getNFA();

        String s = "xyab1";
        assertTrue(n.match(0,s));

         String s2 = "xyab";
        assertFalse(n.match(0,s2));
    }

    @Test
    public void testParseNested2() {
        Node n = new Parse("e?(ab)+(h(ij)?)?c+").getNFA();

        String s = "xyabhic";
        assertFalse(n.match(0, s));

        String s2 = "xyabhc";
        assertTrue(n.match(0, s2));

        String s3 = "xyabzzzc";
        assertFalse(n.match(0, s3));


    }

    @Test
    public void testParseNested3() {
        Node n = new Parse("(a(bc)?d)+").getNFA();

        String s = "adabcd";
        assertTrue(n.match(0, s));

        String s2 = "abdc";
        assertFalse(n.match(0, s2));
    }

    @Test
    public void testWholeGroupOptional() {
        Node n = new Parse("(a(bc)?d)?").getNFA();

        String s = "xxyyzz";
        assertTrue(n.match(0, s));

        String s2 = "abdc";
        assertTrue(n.match(0, s2));
    }

    @Test
    public void testWholeGroupOptional2() {
        Node n = new Parse("(a(b|c)?d)+").getNFA();

        String s = "abcd";
        assertFalse(n.match(0, s));

        String s2 = "123acd";
        assertTrue(n.match(0, s2));
    }
}
