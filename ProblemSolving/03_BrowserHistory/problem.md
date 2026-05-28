# Browser History

## Difficulty: EASY-MEDIUM

## Problem Statement

Design a browser history system. A user starts on a homepage.
Implement the following:

- `BrowserHistory(String homepage)`  — initialise with homepage
- `visit(String url)`                — visit a new url, clearing forward history
- `back(int steps)`                  — go back up to `steps` pages, return current url
- `forward(int steps)`               — go forward up to `steps` pages, return current url

## Example

```
BrowserHistory("leetcode.com")
visit("google.com")
visit("facebook.com")
visit("youtube.com")
back(1)    → "facebook.com"
back(1)    → "google.com"
forward(1) → "facebook.com"
visit("linkedin.com")   ← forward history cleared
forward(2) → "linkedin.com"  (can't go further, stays)
back(2)    → "google.com"
back(7)    → "leetcode.com"  (can't go further back, stays at start)
```

## Key Insight

Two directions of navigation — back and forward.
When you visit a new page, forward history is ERASED.

What structure naturally supports "go back" and "go forward"
with an erase-forward behaviour on new visit?

## What structures to combine

Think about what each direction represents:
- "Back" history = everything you came FROM
- "Forward" history = everything you CAN go to (until erased)

Each direction is independent. Each is a natural fit for one structure.
When you visit a new page: push to back, CLEAR forward.

## Why this matters

Real-world design problem. The interviewer watches HOW you model it.
Clean separation of concerns: back stack vs forward stack.
Naming, method clarity, and handling edge cases (can't go back further).
