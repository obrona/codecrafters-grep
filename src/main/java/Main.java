
import Parser.Parse;
import State.State;

public class Main {
    public static void main(String[] args) {
        String pattern = "a";
        Parse parse = new Parse(pattern);

        String s = "cat";
        System.out.println(parse.match(new State(s)));
    }
}
