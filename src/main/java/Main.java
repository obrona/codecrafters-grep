
import Parser.Parse;
import Node.Node;

public class Main {
    public static void main(String[] args) {
        String pattern = "a";
        Parse parse = new Parse(pattern);

        String s = "cat";
        Node n = parse.getNFA();
        System.out.println(n.match(0, s));
    }
}
