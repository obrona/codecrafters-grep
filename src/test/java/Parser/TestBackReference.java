package Parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Node.Node;
import State.State;

public class TestBackReference {
    @Test
    public void testSimpleBackReference() {
        Node n = new Parse("(a)b\\1").getNFA();
        assertTrue(n.match(new State("aba")));
        assertFalse(n.match(new State("abb")));
    }

    @Test
    public void testMultipleBackReferences() {
        Node n = new Parse("(\\w+)-(\\w+)-\\1-\\2").getNFA();
        assertTrue(n.match(new State("foo-bar-foo-bar")));
        assertFalse(n.match(new State("foo-bar-foo-baz")));
    }

    @Test
    public void testNestedBackReference() {
        String pattern = "('(cat) and \\2') is the same as \\1";
        Parse p = new Parse(pattern);
        Node n = p.getNFA();
        assertTrue(n.match(new State("'cat and cat' is the same as 'cat and cat'")));
        assertFalse(n.match(new State("'cat and dog' is the same as 'cat and dog'")));
    }

    @Test
    public void testNestedBackReference2() {
        String pattern = "((\\w+) \\2) and \\1";
        Parse p = new Parse(pattern);
        Node n = p.getNFA();
        assertTrue(n.match(new State("cat cat and cat cat")));
        assertFalse(n.match(new State("cat dog and cat cat")));
        assertFalse(n.match(new State("cat dog and cat dog")));
    }

    @Test
    public void testNestedBackReference3() {
        String pattern = "((a+)(b)\\2) \\1";
        Parse p = new Parse(pattern);
        Node n = p.getNFA();
        assertTrue(n.match(new State("aba aba")));
        assertTrue(n.match(new State("aabaa aabaa")));
        assertFalse(n.match(new State("aabaa aba")));
    }

    @Test
    public void testNestedBackReference4() {
        String pattern = "((a+)(b)\\2\\3) \\1";
        Parse p = new Parse(pattern);
        Node n = p.getNFA();
        assertTrue(n.match(new State("abab abab")));
        assertFalse(n.match(new State("aaabaaab x")));
    }



    @Test
    public void testBackReferenceToEarlierGroup() {
        Node n = new Parse("(a)|b|c\\1").getNFA();

        // is true because "a" in "ca" matches the branch (a).
        assertTrue(n.match(new State("ca")));
        assertTrue(n.match(new State("b")));
        assertTrue(n.match(new State("a")));

        n = new Parse("^((a)|b|c\\2)").getNFA();
        assertFalse(n.match(new State("ca")));
    }

    @Test
    public void testAlternationWithBackReferences() {
        Node referenceFirstBranch = new Parse("^((cat)|(dog))\\2$").getNFA();
        assertTrue(referenceFirstBranch.match(new State("catcat")));
        assertFalse(referenceFirstBranch.match(new State("dogdog")));

        Node referenceSecondBranch = new Parse("^((cat)|(dog))\\3$").getNFA();
        assertTrue(referenceSecondBranch.match(new State("dogdog")));
        assertFalse(referenceSecondBranch.match(new State("catcat")));
    }

    @Test
    public void testComplexBackReference() {
        Node n = new Parse("((a|b)+ \\2) [xy]+ \\1").getNFA();
        assertTrue(n.match(new State("a a y a a")));
        assertFalse(n.match(new State("a a y b b")));
    }

    @Test
    public void testCaptureInsideRepeatedGroupUsesLastIteration() {
        Node n = new Parse("^((a|b)+)\\2$").getNFA();
        assertTrue(n.match(new State("abb")));  // group 2's last capture is "b"
        assertFalse(n.match(new State("aba"))); // group 2's last capture is "b", not "a"
    }

    @Test
    public void testNestedBackReferencesWithCharacterGroupsAndQuantifiers() {
        Node n = new Parse("^(([ab]+)([xy]*)\\2\\3) \\1$").getNFA();

        assertTrue(n.match(new State("abxyabxy abxyabxy")));
        assertTrue(n.match(new State("aaxxaaxx aaxxaaxx")));
        assertFalse(n.match(new State("abxyabxx abxyabxx")));
        assertFalse(n.match(new State("abxyabxy abxyabyx")));
    }
}
