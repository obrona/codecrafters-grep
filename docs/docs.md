### Grammar

The parser implements the following grammar (written in EBNF):

```ebnf
pattern        = alternation ;
alternation    = concatenation, { "|", concatenation } ;
concatenation  = quantified, { quantified } ;
quantified     = atom, [ quantifier ] ;

quantifier     = "?"
               | "+"
               | "*"
               | "{", integer, "}"
               | "{", integer, ",", integer, "}"
               | "{", integer, ",", "}" ;

atom           = literal
               | "."
               | "^"
               | "$"
               | "\\d"
               | "\\w"
               | backreference
               | character-group
               | capture-group ;

capture-group  = "(", alternation, ")" ;
character-group = "[", characters, "]"
                | "[^", characters, "]" ;
backreference  = "\\", integer ;
integer        = digit, { digit } ;
```

`literal` is a character without special meaning in the position where it
appears. `characters` is the raw list of characters accepted (or rejected) by
a character group; ranges such as `a-z` are not expanded by the parser.

The supported atoms and operators have these meanings:

* `\d` matches a Unicode digit, as defined by Java's `Character.isDigit`.
* `\w` matches a Unicode letter or digit, or an underscore, as defined by
  Java's `Character.isLetterOrDigit`.
* `.` matches any character.
* `^` and `$` match the start and end of the input respectively.
* `[abc]` matches one listed character; `[^abc]` matches one character not listed.
* `(expr)` creates a capture group and groups an expression.
* `\1`, `\2`, etc. match the text most recently captured by that numbered group.
* `expr?` matches zero or one occurrence.
* `expr+` matches one or more occurrences.
* `expr*` matches zero or more occurrences.
* `expr{n,m}` matches at least `n` and at most `m` occurrences.
* `expr{n}` matches exactly `n` occurrences.
* `expr{n,}` matches at least `n` occurrences.
* `a|b` matches either the left or right alternative.

### Operator precedence

From highest precedence (binds most tightly) to lowest:

1. Parenthesized capture groups: `(expr)`
2. Postfix quantifiers: `?`, `+`, `*`, `{n}`, `{n,m}`, `{n,}`
3. Concatenation: adjacent expressions such as `abc`
4. Alternation: `|`

For example, `ab+|cd` is parsed as `(a(b+))|(cd)`, while `(ab)+|cd`
quantifies the whole `ab` capture group.

Expressions quantified by `*`, `+`, or `{...}` must consume at least one character on every successful match. Zero-width quantified expressions such as `(a?)*`, `(a?)+`, and `(a?){2,}` are invalid and may cause infinite recursion. An expression such as `(ab?){2}` is valid because every repetition consumes at least the mandatory `a`.

For backreferences, capture group are numbered by the preorder traversal of the () tree.<br>
Eg for reference ((a)(b))
1. ((a)(b))
2. (a)
3. (b) 

In (a|b)+\1, the backreference \1 captures only the single character matched by the group on its final (most recent) iteration of the + loop — not the whole string consumed by (a|b)+.

### Rules

* Only one postfix quantifier may follow an atom. Chained quantifiers such as
  `a++` and `a?+` are invalid.
* Alternation may be chained: `a|b|c` and `(ab+)|c|d` are valid.
* Each side of `|`, and the contents of every capture group, must be non-empty.

### Example

* (ab){1,3}
* (ab)|c+|d
