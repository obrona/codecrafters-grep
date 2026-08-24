package Parser;

import java.util.ArrayList;
import java.util.HashMap;

import static Parser.Utils.matchBraces;
import Node.Node;
import Pair.Pair;
import Node.AlphaNumericNode;
import Node.CharNode;
import Node.DigitNode;
import Node.EndNode;
import Node.EndStringNode;
import Node.PosCharGroupNode;
import Node.QuantifierNode;
import Node.StartNode;
import Node.StartStringNode;
import Node.WildcardNode;
import Node.NegCharGroupNode;
import State.State;

// invariant: each expr has exactly 1 node as the start (entry into the expr) and 1 node
// as the end (exit from the expr)

public class Parse {
    String pattern;
    HashMap<Integer,Integer> braces;

    public Parse(String pattern) {
        this.pattern = pattern;
        braces = matchBraces(pattern);
    }

    boolean isUnaryOp(char c) {
        return c == '?' || c == '+' || c == '*' || c == '{';
    }


    // exprs.size() must be at least 2
    Pair<Node, Node> parseAlt(ArrayList<Pair<Node,Node>> exprs) {
        Node start = new Node();
        Node end = new Node();
        
        for (Pair<Node,Node> p : exprs) {
            start.addNext(p.first);
            p.second.addNext(end);
        }

        return new Pair<>(start, end);
    }

    Pair<Node,Node> parsePlus(Pair<Node,Node> expr) {
        Node repeat = new Node();
        expr.second.addNext(repeat);
        repeat.addNext(expr.first);
        return new Pair<>(expr.first, repeat);
    }

    Pair<Node,Node> parseQuestionMark(Pair<Node,Node> expr) {
        Node start = new Node();
        Node end = new Node();
        start.addNext(expr.first);
        start.addNext(end);
        expr.second.addNext(end);
        return new Pair<>(start, end);
    }

    Pair<Node,Node> parseStar(Pair<Node,Node> expr) {
        Node start = new Node();
        Node end = new Node();
        start.addNext(expr.first);
        start.addNext(end);
        expr.second.addNext(end);
        end.addNext(expr.first);
        return new Pair<>(start, end);
    }

    Pair<Node,Node> parseQuantifierNode(Pair<Node,Node> expr, int n, int m) {
        QuantifierNode node = new QuantifierNode(n, m);
        node.addRepeat(expr.first);
        expr.second.addNext(node);
        return new Pair<>(node, node);
    }

    Pair<Node,Node> parseChar(char c) {
        Node n = new CharNode(c);
        return new Pair<>(n, n);
    }

    Pair<Node,Node> parseDigit() {
        Node n = new DigitNode();
        return new Pair<>(n,n);
    }

    Pair<Node,Node> parseAlphaNumeric() {
        Node n = new AlphaNumericNode();
        return new Pair<>(n,n);
    }

    Pair<Node,Node> parsePosCharGroup(String chars) {
        Node n = new PosCharGroupNode(chars);
        return new Pair<>(n, n);
    }

    Pair<Node,Node> parseNegCharGroup(String chars) {
        Node n = new NegCharGroupNode(chars);
        return new Pair<>(n, n);
    }

    Pair<Node,Node> parseStartString() {
        Node n = new StartStringNode();
        return new Pair<>(n, n);
    }

    Pair<Node,Node> parseEndString() {
        Node n = new EndStringNode();
        return new Pair<>(n, n);
    }

    Pair<Node,Node> parseWildCard() {
        Node n = new WildcardNode();
        return new Pair<>(n, n);
    }

    Pair<Node,Node> concatPair(Pair<Node,Node> expr1, Pair<Node,Node> expr2) {
        if (expr1 == null) return expr2;
        if (expr2 == null) return expr1;
        
        expr1.second.addNext(expr2.first);
        return new Pair<>(expr1.first, expr2.second);
    }

   

    Pair<Node,Node> parse(int s, int e, String pattern) {
        ArrayList<Pair<Node,Node>> alts = new ArrayList<>();
        Pair<Node,Node> expr = null;

        int ptr = s;
        while (ptr <= e) {
            char c = pattern.charAt(ptr);
            
            // if we encounter a |, expr stores the expr in the branching path so just push into alts
            if (c == '|') {
                assert(expr != null);
                alts.add(expr);
                
                expr = null;
                ptr++;
                continue;
            }
            
            Pair<Node,Node> p = null;
            if (c == '(') {
                int braceEnd = braces.get(ptr);
                p = parse(ptr + 1, braceEnd - 1, pattern);
                ptr = braceEnd + 1;
            } else if (c == '[') {
                int end = braces.get(ptr);
                if (pattern.charAt(ptr + 1) == '^') {
                    p = parseNegCharGroup(pattern.substring(ptr + 2, end));
                } else {
                    p = parsePosCharGroup(pattern.substring(ptr + 1, end));
                }
                ptr = end + 1;
            } else if (c == '\\') {
                int charClass = pattern.charAt(ptr + 1);
                if (charClass == 'd') {
                    p = parseDigit();
                } else if (charClass == 'w') {
                    p = parseAlphaNumeric();
                }
                ptr += 2;
            } else if (c == '^') {
                p = parseStartString();
                ptr++;
            } else if (c == '$') {
                p = parseEndString();
                ptr++;
            } else if (c == '.') {
                p = parseWildCard();
                ptr++;
            } else {
                p = parseChar(pattern.charAt(ptr));
                ptr++;
            } 
            assert(p != null);

            // now check for unary ops.
            // unary ops are ?, +, *, {n,m} ...
            if (ptr <= e && isUnaryOp(pattern.charAt(ptr))) {
                char op = pattern.charAt(ptr);
                if (op == '?') {
                    p = parseQuestionMark(p);
                    ptr++;
                } else if (op == '+') {
                    p = parsePlus(p);
                    ptr++;
                } else if (op == '*') {
                    p = parseStar(p);
                    ptr++;
                } else if (op == '{') {
                    int end = braces.get(ptr);
                    int[] range = Utils.getRangeForQuantiferString(pattern.substring(ptr + 1, end));
                    p = parseQuantifierNode(p, range[0], range[1]);
                    ptr = end + 1;
                }
            }

            expr = concatPair(expr, p);
        }

        // now check the last expr, remember we only add when encountering a |
        // so the last expr eg. e1|e2 has not been added
        if (alts.size() > 0) {
            alts.add(expr);
        }
        
        // now check if we have alts
        if (alts.size() > 0) {
            return parseAlt(alts);
        } else {
            return expr;
        }
    }

    public Node getNFA() {
        Pair<Node,Node> expr = parse(0, pattern.length() - 1, pattern);
        Node n = new StartNode();
        Node end = new EndNode();
        n.addNext(expr.first);
        expr.second.addNext(end);
        return n;
    }

    public boolean match(State state) {
        return getNFA().match(state);
    }
 

}
