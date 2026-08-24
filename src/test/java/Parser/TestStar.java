package Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Node.Node;
import State.State;

public class TestStar {
    @Test
    public void testStarMatchesZeroOccurrences() {
        Node n = new Parse("ab*c").getNFA();

        assertTrue(n.match(new State("ac")));
    }

    @Test
    public void testStarMatchesOneOccurrence() {
        Node n = new Parse("ab*c").getNFA();

        assertTrue(n.match(new State("abc")));
    }

    @Test
    public void testStarMatchesMultipleOccurrences() {
        Node n = new Parse("ab*c").getNFA();

        assertTrue(n.match(new State("abbbbc")));
    }

    @Test
    public void testStarRejectsNonMatchingInput() {
        Node n = new Parse("^ab*c$").getNFA();

        assertFalse(n.match(new State("abdc")));
    }

    @Test
    public void testStarAppliesToWholeGroup() {
        Node n = new Parse("^(ab)*c$").getNFA();

        assertTrue(n.match(new State("abababc")));
        assertTrue(n.match(new State("c")));
        assertFalse(n.match(new State("abac")));
    }

    @Test
    public void testStarAppliesToCharacterClass() {
        Node n = new Parse("^[xy]*z$").getNFA();

        assertTrue(n.match(new State("xyyxyz")));
        assertTrue(n.match(new State("z")));
        assertFalse(n.match(new State("xyzx")));
    }
}
