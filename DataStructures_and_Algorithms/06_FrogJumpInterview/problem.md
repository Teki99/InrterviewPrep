# Frog Jump Problem

## Problem Statement

Given an array of `n` integers, a frog starts at index `0`.

At each position `i`, the frog can jump to:
- `i + arr[i]`  (forward by arr[i] steps)
- `i - arr[i]`  (backward by arr[i] steps)

Return `true` if the frog can reach the last index `n-1`, `false` otherwise.
Jumps that go out of bounds are not allowed.

## Example

```
Input:  [4, 2, 3, 0, 3, 1, 2]
Output: true
Explanation: 0 --(+4)--> 4 --(-3)--> 1 --(+2)--> 3 --(+3)--> 6 ✓

Input:  [0, 1]
Output: false
Explanation: Frog is stuck at index 0 (arr[0] = 0, both jumps stay at 0).

Input:  [1, 0]
Output: true
Explanation: 0 --(+1)--> 1 ✓
```

## What to think about

- The frog can go BOTH forward and backward — this is NOT a greedy problem.
- Greedy doesn't work here because going backward might be the only way forward.
- Think of each index as a NODE in a graph.
- From node i, there are edges to i+arr[i] and i-arr[i] (if in bounds).
- Question becomes: is there a path from node 0 to node n-1?
- You MUST track visited nodes to avoid infinite loops.

## Why this is BFS/DFS, not Greedy

In a forward-only Jump Game, greedy works — you track max reach in one pass.
Here, because the frog can go backward and revisit areas,
greedy breaks completely. You need to EXPLORE all reachable positions.
That exploration is exactly what BFS and DFS do.
