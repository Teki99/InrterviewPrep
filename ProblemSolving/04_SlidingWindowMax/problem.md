# Sliding Window Maximum

## Difficulty: MEDIUM

## Problem Statement

Given an array of integers and a window size `k`,
return an array of the maximum value in each sliding window of size k.

## Example

```
Input:  nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
Output: [3, 3, 5, 5, 6, 7]

Window [1,3,-1]  → max = 3
Window [3,-1,-3] → max = 3
Window [-1,-3,5] → max = 5
Window [-3,5,3]  → max = 5
Window [5,3,6]   → max = 6
Window [3,6,7]   → max = 7
```

## Brute Force

For each window, scan all k elements and find max.
- Time: O(n * k)
- Not acceptable for large inputs.

## Key Insight

You don't need to re-scan the whole window each time.
The window slides — one element leaves, one enters.
Can you maintain the maximum WITHOUT scanning everything?

Think about elements that can NEVER be the maximum:
- If element X is smaller than element Y, and Y entered AFTER X,
  can X ever be the maximum while Y is still in the window?
  NO — X is useless. Remove it.

This is a MONOTONIC DEQUE — it keeps only elements that
COULD potentially be the maximum, in decreasing order.

## What structures to combine

- Array (or list) for output.
- Deque (double-ended queue) as a monotonic structure:
  - Front = index of current maximum
  - Remove from front when it slides out of window
  - Remove from back when a larger element enters

## Time & Space Complexity

- Brute Force: O(n*k) / O(1)
- Deque:       O(n)   / O(k)

## Why this matters

Introdućeš the MONOTONIC DEQUE — a less obvious but powerful pattern.
The trick is: instead of storing values, store INDICES in the deque.
This lets you check if the front element has slid out of the window.
