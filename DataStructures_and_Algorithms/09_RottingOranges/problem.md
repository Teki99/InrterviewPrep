# Rotting Oranges

## Difficulty: HARD

## Problem Statement

Given a grid where:
- 0 = empty cell
- 1 = fresh orange
- 2 = rotten orange

Every minute, a rotten orange makes all 4-directionally adjacent fresh oranges rotten.
Return the minimum number of minutes until no fresh orange remains.
If it is impossible, return -1.

## Example

```
Input:  [[2,1,1],
         [1,1,0],
         [0,1,1]]
Output: 4

Input:  [[2,1,1],
         [0,1,1],
         [1,0,1]]
Output: -1
Explanation: The orange in the bottom left is isolated.

Input:  [[0,2]]
Output: 0
```

## What to think about

- Multiple rotten oranges spread simultaneously — this is MULTI-SOURCE BFS.
- Start by adding ALL rotten oranges to the queue at once (level 0).
- Each BFS level = 1 minute passing.
- Count levels until queue is empty.
- After BFS, if any fresh orange remains, return -1.

## Key new concept: Multi-Source BFS

In GraphHasPath and FrogJump, BFS starts from ONE source.
Here it starts from MULTIPLE sourćeš simultaneously.

```java
// Add ALL starting points before BFS begins
for (every rotten orange at (r,c)) {
    queue.add(new int[]{r, c});
    visited[r][c] = true;
}
// Then run normal BFS — each level = 1 minute
```

## Why this matters

Multi-source BFS is a pattern that appears frequently in interviews.
If you understand single-source BFS (FrogJump), this is a small but
important conceptual step. The template is identical — only the
initialisation changes (multiple starting nodes instead of one).
