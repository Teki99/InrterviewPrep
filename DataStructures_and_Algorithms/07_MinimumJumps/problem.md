# Minimum Number of Jumps

## Difficulty: HARD

## Problem Statement

Given an array of non-negative integers, where each element is the max jump length,
return the MINIMUM number of jumps needed to reach the last index.

You can assume that it is always possible to reach the last index.

## Example

```
Input:  [2, 3, 1, 1, 4]
Output: 2
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

Input:  [2, 3, 0, 1, 4]
Output: 2
```

## What to think about

- This is no longer just "can we reach?" but "what is the cheapest way?"
- How does BFS naturally model "minimum steps"?
- Can Greedy work here too? What would it track?
- Think in terms of "levels" — all positions reachable in 1 jump,
  then all reachable in 2 jumps, etc.

## Why this matters

This is the natural hard follow-up to Frog Jump.
Interviewers often ask this after you solve the basic version.
Solving this means you truly understand the problem space,
not just memorized the easy solution.

## Hint

BFS approach:
- Treat each "range of reachable positions" as one BFS level.
- Each level = one jump.
- Count levels until you pass the last index.
