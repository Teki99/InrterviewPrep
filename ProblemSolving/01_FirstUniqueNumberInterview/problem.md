# First Unique Number in a Stream

## Problem Statement

Numbers arrive one by one through a stream.
Design a data structure that supports two operations:

- `insert(int num)`        — adds a number from the stream
- `getFirstUnique()`       — returns the first inserted number that has NOT repeated yet
                             (i.e. appeared exactly once so far, and was inserted earliest)

If no unique number exists, return -1.

## Example

```
insert(5)   → stream: [5]          → getFirstUnique() = 5
insert(3)   → stream: [5, 3]       → getFirstUnique() = 5
insert(5)   → stream: [5, 3, 5]    → getFirstUnique() = 3  (5 is now duplicate)
insert(7)   → stream: [5, 3, 5, 7] → getFirstUnique() = 3
insert(3)   → stream: [...]        → getFirstUnique() = 7  (3 is now duplicate)
```

## Key Insight

Two things need to be tracked simultaneously:
1. HOW MANY TIMES has each number appeared?  → frequency
2. IN WHAT ORDER did they first appear?       → insertion order

The challenge: getFirstUnique() must be fast.
Iterating through everything on each call is too slow.
