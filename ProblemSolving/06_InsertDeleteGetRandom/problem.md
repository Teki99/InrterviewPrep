# Insert Delete GetRandom O(1)

## Difficulty: HARD

## Problem Statement

Design a data structure that supports ALL of the following in O(1) average time:

- `insert(int val)`    — inserts val if not present, returns true/false
- `remove(int val)`    — removes val if present, returns true/false
- `getRandom()`        — returns a random element with equal probability

## Example

```
insert(1)  → true,   set: {1}
insert(2)  → true,   set: {1, 2}
insert(2)  → false   (already exists)
getRandom() → 1 or 2 with equal probability
remove(1)  → true,   set: {2}
getRandom() → 2 (only element)
```

## Why is this hard?

- HashMap gives O(1) insert and remove, but O(n) getRandom (no index access).
- ArrayList gives O(1) insert and getRandom, but O(n) remove (shift elements).
- HashSet gives O(1) insert and remove, but no O(1) random access by index.

No single structure solves all three in O(1).

## Key Insight

getRandom() needs index-based access → ArrayList.
remove() needs O(1) lookup → HashMap.

But removing from middle of ArrayList is O(n)...

TRICK: When removing an element, swap it with the LAST element,
then remove from the end. End removal is O(1).
HashMap keeps track of each element's current index.

## What structures to combine

- ArrayList         → stores elements, enables O(1) random access by index
- HashMap<val, idx> → maps each value to its index in the ArrayList

insert(val):
  Add to end of ArrayList. Store index in HashMap.

remove(val):
  Find index via HashMap.
  Swap element with LAST element in ArrayList.
  Update HashMap for the swapped element's new index.
  Remove last element from ArrayList. Remove val from HashMap.

getRandom():
  Return ArrayList.get(randomIndex).

## Time & Space Complexity

- insert:    O(1) average
- remove:    O(1) average
- getRandom: O(1)
- Space:     O(n)

## Why this matters

Most elegant O(1) trick in "design" problems.
The swap-with-last pattern appears in multiple interview questions.
Shows you can think beyond "which structure fits" to "how to combine them cleverly".
