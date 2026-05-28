# Min Stack

## Difficulty: EASY-MEDIUM

## Problem Statement

Design a stack that supports the following operations in O(1):

- `push(int val)`   — pushes val onto the stack
- `pop()`           — removes the top element
- `top()`           — returns the top element without removing it
- `getMin()`        — returns the minimum element in the stack

## Example

```
push(5)  → stack: [5]          getMin() = 5
push(3)  → stack: [5, 3]       getMin() = 3
push(7)  → stack: [5, 3, 7]    getMin() = 3
pop()    → stack: [5, 3]        getMin() = 3
pop()    → stack: [5]           getMin() = 5   ← minimum restored!
```

## Key Insight

A single stack cannot track minimum in O(1) after pop().
When you pop() the current minimum, how do you know what the PREVIOUS minimum was?

You need to remember the history of minimums.

## What structures to combine

Think about what each structure is responsible for:
- One structure holds the actual elements (normal stack behaviour).
- One structure tracks the minimum AT EACH POINT IN TIME.

## Why this matters

Classic "Logic and Maintainable" problem.
The trick is not algorithmic — it's recognising that
one structure cannot do two jobs efficiently at the same time.
Split the responsibility cleanly between two structures.
