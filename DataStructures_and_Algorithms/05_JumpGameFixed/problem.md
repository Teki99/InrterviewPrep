# Jump Game - Fixed Jump Size

## Difficulty: MEDIUM

## Problem Statement

Given an array of `n` integers and a fixed jump size `k`,
a frog starts at index 0 and can jump EXACTLY `k` steps at a time.
Some positions are blocked (value = 0), others are free (value = 1).

Return true if the frog can reach the last index, false otherwise.

## Example

```
Input:  array = [1, 1, 0, 1, 1], k = 2
Output: true
Explanation: 0 -> 2 is blocked? No — index 2 has value 0 (blocked).
             Jump from 0 by k=2 -> index 2 -> BLOCKED. Return false.

Input:  array = [1, 0, 1, 0, 1], k = 2
Output: true
Explanation: 0 -> 2 -> 4. All free. Return true.

Input:  array = [1, 1, 1, 0, 1], k = 2
Output: false
Explanation: 0 -> 2 -> 4. Index 3 is never visited, index 4 is reachable.
             Wait — recheck: 0->2->4, index 3 skipped. Output: true.
             Change k=1: 0->1->2->3 BLOCKED. Output: false.
```

## What to think about

- The jump is always exactly k — no choice involved.
- Which indićeš will the frog ever visit?
- What condition makes it impossible?

## Why this matters

Simplified version of Frog Jump — removes the variable jump complexity.
Forćeš you to think about reachability and blocking before dealing
with the harder case where every cell has a different jump length.
