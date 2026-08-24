package Parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import Pair.Pair;

public class Utils {

    static boolean isStartBrace(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    static boolean isEndBrace(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    // get the matching brace, for both start brace and end brace.
    // for all braces type eg (), [], {}
    public static HashMap<Integer, Integer> matchBraces(String pattern) {
        HashMap<Integer, Integer> out = new HashMap<>();
        ArrayList<Pair<Integer, Character>> stack = new ArrayList<>();
        
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (isStartBrace(c)) {
                stack.add(new Pair<>(i, c));
            } else if (isEndBrace(c)) {
                Pair<Integer, Character> p = stack.removeLast();
                out.put(p.first, i);
                out.put(i, p.first);
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

            if (pattern.charAt(ptr) == '|') {
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

    public static int[] getRangeForQuantiferString(String s) {
        if (!s.contains(",")) {
            int n = Integer.parseInt(s);
            return new int[] {n, n};
        } else {
            int[] ans = Arrays.stream(s.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
            if (ans.length == 1) {
                return new int[] {ans[0], 2000000000};
            } else {
                return ans;
            }
        }
    }
}
    