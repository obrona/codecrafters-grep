### Grammar
* \d: match [0-9]
* \w: match [a-z],[A-Z],[0-9] and _
* ^
* $
* \+: match one or more times
* ?
* |
* \*: match zero or more times
* {n,m}: match at least n and at most m times
* {n}: match exactly n times
* {n,}: match at least n times
* backreferences: eg `cat and \1` matches `cat and cat`

Expressions quantified by `*`, `+`, or `{...}` must consume at least one character on every successful match. Zero-width quantified expressions such as `(a?)*`, `(a?)+`, and `(a?){2,}` are invalid and may cause infinite recursion. An expression such as `(ab?){2}` is valid because every repetition consumes at least the mandatory `a`.

For backreferences, capture group are numbered by the preorder traversal of the () tree.<br>
Eg for reference ((a)(b))
1. ((a)(b))
2. (a)
3. (b) 

In (a|b)+\1, the backreference \1 captures only the single character matched by the group on its final (most recent) iteration of the + loop — not the whole string consumed by (a|b)+.

### Rules
* no \+\+
* no ?\+ eg. a?\+
    * i.e for ?,\+ can only have 1 at a time
* a|b|c, (ab+)|c|d are all allowed, i.e can chain |

### Example
* (ab){1,3}
* (ab)|c+|d

### Thoughts
* consider operator priority
    * (expr) most important
    * next is |
    * next is ?, +
    * lastly is just the characters

* Problem is operators are in the middle (for |) and at the back (for ?,+)
