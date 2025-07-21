package Parser;

import java.util.ArrayList;
import java.util.HashMap;
import Pair.Pair;

public class Utils {

    // get the matching brace, for both start brace and end brace
    public static HashMap<Integer, Integer> matchBraces(String pattern) {
        HashMap<Integer, Integer> out = new HashMap<>();
        ArrayList<Integer> stack = new ArrayList<>();
        
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '(') {
                stack.add(i);
            } else if (pattern.charAt(i) == ')') {
                int pos = stack.remove(stack.size() - 1);
                out.put(pos, i);
                out.put(i, pos);
            }
        }

        return out;
    }

    // get all | in a pattern
    // impt: if we encounter (, we skip to its end )
    public static ArrayList<Integer> getAltOp(int s, int e, String pattern, HashMap<Integer, Integer> braces) {
        ArrayList<Integer> out = new ArrayList<>();

        int ptr = s;
        while (ptr < e) {
            if (pattern.charAt(ptr) == '(') {
                ptr = braces.get(ptr) + 1;
                continue;
            }

            if (ptr == '|') {
                out.add(ptr);
            }
            
            ptr++;
        }

        return out;
    }

    // get all ?+
    // if we encounter (, we skip to its end
    public static ArrayList<Pair<Integer, Character>> getPlusAndQuestionMarkOps(int s, int e, String pattern, HashMap<Integer, Integer> braces) {
        ArrayList<Pair<Integer, Character>> out = new ArrayList<>();

        int ptr = s;
        while (ptr < e) {
            if (pattern.charAt(ptr) == '(') {
                ptr = braces.get(ptr) + 1;
                continue;
            }

            char c = pattern.charAt(ptr);
            if (c == '?' || c == '+') {
                out.add(new Pair<>(ptr, c));
            }

            ptr++;
        }

        return out;

    }
}
    