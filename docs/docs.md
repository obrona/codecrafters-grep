### Grammer
* \d: match [0-9]
* \w: match [a-z],[A-Z],[0-9] and _
* ^
* $
* \+
* ?
* |
* {n,m}: match pattern at least n and at most m times. n cannot be 0.

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