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
        // Group 1: ('(cat) and \2')
        // Group 2: (cat)
        // \2 refers to "cat"
        // \1 refers to "'cat and cat'"
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
        assertFalse(n.match(new State("ca")));
    }
}