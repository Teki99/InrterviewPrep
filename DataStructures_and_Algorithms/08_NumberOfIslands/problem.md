# Number of Islands

## Difficulty: HARD

## Problem Statement

Given a 2D grid of '1's (land) and '0's (water),
return the number of islands.

An island is surrounded by water and is formed by connecting
adjacent land cells horizontally or vertically.

## Example

```
Input:  [["1","1","1","1","0"],
         ["1","1","0","1","0"],
         ["1","1","0","0","0"],
         ["0","0","0","0","0"]]
Output: 1

Input:  [["1","1","0","0","0"],
         ["1","1","0","0","0"],
         ["0","0","1","0","0"],
         ["0","0","0","1","1"]]
Output: 3
```

## What to think about

- Iterate through every cell.
- When you find an unvisited '1', you found a new island — increment count.
- Run BFS/DFS from that cell to mark the entire island as visited.
- Continue iterating — next unvisited '1' is a new, separate island.

## Connection to FrogJump

Same BFS + visited pattern.
The difference: instead of asking "can I reach the end from one start",
you're asking "how many separate connected components exist?"
Both use the exact same traversal engine.

## Why this matters

This is one of the most common BFS/DFS interview questions.
Reinforćeš the pattern that BFS is not just for finding ONE path —
it can be used to explore and COUNT connected regions.
