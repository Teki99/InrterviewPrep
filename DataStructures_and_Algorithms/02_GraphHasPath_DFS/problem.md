# Graph Has Path — DFS

## Difficulty: EASY

## Problem Statement

Isti zadatak kao 01_GraphHasPath_BFS:

Given a directed graph as a list of edges and two nodes src and dst,
return true if there is a path from src to dst, false otherwise.

## Constraint

Resi iskljucivo koristeci DFS (rekurziju ili eksplicitni stack).

## Example

```
edges = [[0,1],[1,2],[2,3]], src = 0, dst = 3  →  true
edges = [[0,1],[1,2],[2,3]], src = 3, dst = 0  →  false
```

## DFS template

```java
Set<Integer> visited = new HashSet<>();

boolean dfs(Map<Integer, List<Integer>> graph, int current, int dst) {
    if (current == dst) return true;
    if (visited.contains(current)) return false;

    visited.add(current);

    for (int neighbour : graph.get(current)) {
        if (dfs(graph, neighbour, dst)) return true;
    }
    return false;
}
```

## Cilj ovog zadatka

Resi ISTI problem sa BFS (01) i DFS (ovaj) i upporedi:
- Kod
- Nacin razmisljanja
- Kada se jedno ponasa drugacije od drugog

Zatim pridi na 03_BFSvsDFS_QA i odgovori na pitanja.
