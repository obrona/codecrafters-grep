package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;
import State.State;

public class TestQuantifier {
    @Test
    public void testQuantifierMatchesWithinInclusiveRange() {
        Node n = new Parse("^a{2,4}$").getNFA();

        assertTrue(n.match(new State("aa")));
        assertTrue(n.match(new State("aaa")));
        assertTrue(n.match(new State("aaaa")));
    }

    @Test
    public void testQuantifierRejectsOutsideRange() {
        Node n = new Parse("^a{2,4}$").getNFA();

        assertFalse(n.match(new State("a")));
        assertFalse(n.match(new State("aaaaa")));
    }

    @Test
    public void testQuantifierAppliesToWholeGroup() {
        Node n = new Parse("^(ab){2,3}$").getNFA();

        assertTrue(n.match(new State("abab")));
        assertTrue(n.match(new State("ababab")));
        assertFalse(n.match(new State("ab")));
        assertFalse(n.match(new State("abababab")));
    }

    @Test
    public void testQuantifiedGroupCanContainOptionalExpression() {
        Node n = new Parse("^(ab?){2,3}$").getNFA();

        assertTrue(n.match(new State("aa")));
        assertTrue(n.match(new State("aba")));
        assertTrue(n.match(new State("ababab")));
        assertFalse(n.match(new State("a")));
    }

    @Test
    public void testQuantifierBacktracksToMatchFollowingExpression() {
        Node n = new Parse("^a{2,4}ab$").getNFA();

        assertTrue(n.match(new State("aaab")));
        assertTrue(n.match(new State("aaaab")));
        assertTrue(n.match(new State("aaaaab")));
        assertFalse(n.match(new State("aab")));
        assertFalse(n.match(new State("aaaaaab")));
    }
}
