# Frog Jump — Minimum Steps (Hard Variant)

## Difficulty: VERY HARD

## Problem Statement

Same setup as FrogJump (04):
- Array of n integers, frog starts at index 0.
- From index i, frog can jump to i+arr[i] or i-arr[i].
- Out of bounds jumps are not allowed.

Now the question changes:
Return the MINIMUM number of jumps to reach the last index.
If it is impossible, return -1.

## Example

```
Input:  [4, 2, 3, 0, 3, 1, 2]
Output: 3
Explanation: One possible path in 3 jumps:
             0 --(+4)--> 4 --(-3)--> 1 --(+2)--> 3... keep exploring

Input:  [0, 1]
Output: -1
Explanation: Frog stuck at index 0.
```

## What to think about

- 04_FrogJump asked CAN you reach the end? (BFS yes/no)
- This asks MINIMUM JUMPS to reach the end.
- BFS naturally gives shortest path — each level = one jump.
- Count BFS levels until you reach n-1.

## Key insight

BFS level = number of jumps taken so far.
When you first reach n-1, you've taken the minimum number of jumps.
This is why BFS (not DFS) is the right tool for "minimum steps" questions.

## Connection to everything before

01_GraphHasPath:    BFS — does path exist?
04_FrogJump:        BFS on implicit array graph — does path exist?
05_MinimumJumps:    BFS levels — minimum jumps (forward only)
09_FrogJumpMinSteps BFS levels — minimum jumps (bidirectional) ← hardest

The algorithm is the same. The graph is harder.

## Why this is the hardest

- Bidirectional movement means more possible paths.
- Must track visited to avoid cycles (same as 04).
- Must count BFS levels for minimum (same as 05).
- Combines both challenges at once.
