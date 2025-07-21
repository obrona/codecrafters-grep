package Parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Node.Node;
public class ParseTest {
    
    @Test
    public void testParseChar() {
        String pattern = "a";
        Parse parse = new Parse(pattern);
        
        String s = "cat";
        
        Node n = parse.getNFA();
        assertTrue(n.match(0, s));
    }

}
