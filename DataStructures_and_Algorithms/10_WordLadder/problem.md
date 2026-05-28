# Word Ladder

## Difficulty: VERY HARD

## Problem Statement

Given a beginWord, an endWord, and a wordList,
return the number of words in the shortest transformation sequence
from beginWord to endWord, where each step changes exactly one letter
and every intermediate word must exist in wordList.

Return 0 if no such sequence exists.

## Example

```
Input:  beginWord = "hit", endWord = "cog"
        wordList  = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: "hit" -> "hot" -> "dot" -> "dog" -> "cog"
             5 words in the sequence.

Input:  beginWord = "hit", endWord = "cog"
        wordList  = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: "cog" is not in wordList, no valid sequence.
```

## What to think about

- Each WORD is a NODE in the graph.
- Two words are NEIGHBOURS if they differ by exactly one letter.
- The graph is NEVER explicitly built — neighbours are computed on the fly.
- Use BFS to find the SHORTEST path from beginWord to endWord.
- visited set = set of already used words.

## Connection to FrogJump

FrogJump:   nodes = array indićeš,    edges = i±arr[i]
WordLadder: nodes = words,            edges = one-letter-difference

Both are BFS on an IMPLICIT graph.
The only difference is how neighbours are defined.
The BFS template is identical.

## Why this matters

This is the ultimate test of whether you understand BFS as a
general algorithm, not just as "something you do on arrays or grids".
If you can solve this, you can solve any BFS problem regardless of domain.

## Time Complexity

- O(n * L^2) where n = wordList size, L = word length
- Space: O(n)
