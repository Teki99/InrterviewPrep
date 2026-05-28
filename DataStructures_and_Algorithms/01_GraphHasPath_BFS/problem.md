# Graph Has Path

## Difficulty: EASY

## Problem Statement

Given a directed graph represented as a list of edges and two nodes `src` and `dst`,
return true if there is a path from `src` to `dst`, false otherwise.

## Example

```
edges = [[0,1],[1,2],[2,3]], src = 0, dst = 3
Output: true
Explanation: 0 -> 1 -> 2 -> 3

edges = [[0,1],[1,2],[2,3]], src = 3, dst = 0
Output: false
Explanation: Edges are directed — you cannot go backwards.

edges = [[0,1],[0,2],[1,3],[2,3]], src = 0, dst = 3
Output: true
Explanation: Two paths exist: 0->1->3 and 0->2->3
```

## What to think about

- First, build an adjacency list from the edges.
- Then, from src, explore all reachable nodes.
- Use a visited set to avoid revisiting the same node.
- If you reach dst, return true. If exploration ends without it, return false.

## BFS template (learn this by heart)

```
Queue<Integer> queue = new LinkedList<>();
Set<Integer> visited = new HashSet<>();

queue.add(src);
visited.add(src);

while (!queue.isEmpty()) {
    int node = queue.poll();
    if (node == dst) return true;
    for (int neighbour : graph.get(node)) {
        if (!visited.contains(neighbour)) {
            visited.add(neighbour);
            queue.add(neighbour);
        }
    }
}
return false;
```

## Why this matters

This is the purest form of BFS. No tricks, no implicit graph.
Just: queue + visited + neighbours.
Every BFS problem you will ever solve is a variation of this template.
Learn it here. Apply it everywhere else.
