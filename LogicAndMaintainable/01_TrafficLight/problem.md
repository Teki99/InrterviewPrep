# Traffic Light System

## Difficulty: EASY
## Focus: Enums, state transitions, simple class design

## Problem Statement

Design a traffic light system for a single intersection.

A traffic light cycles through states: GREEN → YELLOW → RED → GREEN.
Each state has a fixed duration in seconds.
The system should support switching to the next state and querying the current state.

## Requirements

- A traffic light has a current state (color)
- Each color has a defined duration:
    GREEN  = 30 seconds
    YELLOW = 5 seconds
    RED    = 30 seconds
- The light can advance to the next state in the cycle
- The system can return how many seconds remain in the current state
- The system can return whether it is currently safe to cross (green = safe)

## Example Usage

```java
TrafficLight light = new TrafficLight();
light.getCurrentState();     // GREEN
light.isSafeToCross();       // true
light.getRemainingSeconds(); // 30
light.advance();
light.getCurrentState();     // YELLOW
light.isSafeToCross();       // false
```

## What to think about

- Should the color and its duration be modeled separately, or together?
- Where does the transition logic live — in the enum or in the class?
- What is the right return type for getCurrentState()?
  (String? int? Something else?)
- Is isSafeToCross() a method on the light, or on the color?

## What the Interviewer Is Looking For

- Using an enum for LightColor (not String, not int)
- Enum carrying its own data (duration) — not just a label
- Clean transition logic: GREEN.next() = YELLOW, not a switch-case in the class
- Self-explanatory method names: isSafeToCross() vs getStatus()
- Single responsibility: TrafficLight manages state, LightColor defines behavior


---

## Priority Guide

**Primary focus** (what matters most):
- Naming of classes, methods, and fields
- Enums for all types and statuses
- Separation of concerns — each class has one job
- Intuitive API — usable without reading the implementation

**Secondary focus** (should be present where it makes sense, not forced):
- OOP — inheritance and interfaces where they genuinely fit
- Design patterns — only if they naturally emerge from the design
