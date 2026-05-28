# Find Median from Data Stream

## Difficulty: HARD

## Problem Statement

Design a data structure that supports:

- `addNum(int num)`  — adds a number from a data stream
- `findMedian()`    — returns the median of all numbers added so far

Median: middle value if odd count, average of two middle values if even count.

## Example

```
addNum(1)           → stream: [1]        findMedian() = 1.0
addNum(2)           → stream: [1,2]      findMedian() = 1.5
addNum(3)           → stream: [1,2,3]    findMedian() = 2.0
addNum(7)           → stream: [1,2,3,7]  findMedian() = 2.5
addNum(5)           → stream: [1,2,3,5,7] findMedian() = 3.0
```

## Brute Force

Sort the array each time findMedian() is called.
- addNum:     O(1)
- findMedian: O(n log n)
Not acceptable for a stream.

## Key Insight

You don't need the full sorted order — just the MIDDLE.

Split the stream into two halves:
- Left half:  the smaller numbers
- Right half: the larger numbers

If you always keep them balanced (sizes differ by at most 1),
the median is always at the TOP of one or both halves.

The top of the left half  = largest of the small numbers  → MAX
The top of the right half = smallest of the large numbers → MIN

What structure gives you the top element in O(1) and insertion in O(log n)?

## What structures to combine

- MaxHeap (PriorityQueue reversed) for the LEFT half  → gives largest of small numbers
- MinHeap (PriorityQueue default) for the RIGHT half → gives smallest of large numbers

Balance rule after each insert:
  left.size() == right.size()          → median = (left.top + right.top) / 2
  left.size() == right.size() + 1      → median = left.top

## Time & Space Complexity

- addNum:     O(log n)
- findMedian: O(1)
- Space:      O(n)

## Why this matters

Two heaps working together is one of the most elegant structure combinations.
Neither heap alone can answer the question efficiently.
Together, they maintain exactly the information needed — nothing more.
