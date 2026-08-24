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

Expressions quantified by `*`, `+`, or `{...}` must consume at least one character on every successful match. Zero-width quantified expressions such as `(a?)*`, `(a?)+`, and `(a?){2,}` are invalid and may cause infinite recursion. An expression such as `(ab?){2}` is valid because every repetition consumes at least the mandatory `a`.

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
