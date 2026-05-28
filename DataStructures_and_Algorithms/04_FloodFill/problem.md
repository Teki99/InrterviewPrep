# Flood Fill

## Difficulty: EASY

## Problem Statement

Given a 2D grid of integers (an image), a starting cell (sr, sc),
and a new color, fill all cells connected to the starting cell
(4-directional: up, down, left, right) that share the same original color.

Return the modified grid.

## Example

```
Input:  image = [[1,1,1],
                 [1,1,0],
                 [1,0,1]],  sr=1, sc=1, color=2

Output:         [[2,2,2],
                 [2,2,0],
                 [2,0,1]]

Explanation: Starting from (1,1), all connected 1s are changed to 2.
             The bottom-right 1 is NOT connected (separated by 0s).
```

## What to think about

- From the starting cell, explore all 4 neighbours.
- Only visit cells that have the SAME original color and are not yet visited.
- This is BFS/DFS on a 2D grid — same algorithm as GraphHasPath,
  just the "neighbours" are now up/down/left/right cells.

## Key difference from GraphHasPath

- Graph was explicit (edge list given to you).
- Here the graph is IMPLICIT — you derive neighbours from position.
- This is exactly what happens in FrogJump:
  neighbours of i are i+arr[i] and i-arr[i], derived from the array.

## Directions pattern (learn this)

```java
int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
for (int[] dir : directions) {
    int nr = row + dir[0];
    int nc = col + dir[1];
    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
        // valid neighbour
    }
}
```

## Why this matters

Introdućeš the IMPLICIT GRAPH concept — neighbours computed from position,
not from an explicit edge list. FrogJump is the same idea on a 1D array.
Also reinforćeš the visited pattern to avoid revisiting cells.
