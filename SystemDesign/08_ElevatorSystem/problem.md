# Elevator System

## Difficulty: LARGE (similar scope to Battleship)
## Patterns: Strategy + Observer + State + Factory

## Problem Statement

Design an elevator system for a building.
The system manages multiple elevators, handles floor requests,
schedules which elevator responds, and tracks elevator state.

---

## Requirements

### Building & Elevators
- A building has N floors and M elevators
- Each elevator has: a capacity (max people), current floor, current state
- Elevators can be of different types (standard, express, freight)
- Express elevators only stop at designated express floors

### Elevator States
  IDLE:       stationary, waiting for a request
  MOVING_UP:  traveling upward toward a target floor
  MOVING_DOWN: traveling downward toward a target floor
  DOOR_OPEN:  stopped, doors open, boarding/exiting
  MAINTENANCE: out of service

Rules:
- An elevator in MAINTENANCE cannot be assigned requests
- DOOR_OPEN → doors close after a timeout or when close button pressed
- An elevator processes all requests in its current direction
  before reversing (scan algorithm)

### Requests
Two types of requests:
  EXTERNAL: from a floor panel (person presses UP or DOWN button)
            → system assigns the best elevator
  INTERNAL: from inside an elevator (person presses a floor number)
            → goes to that elevator's queue

### Scheduling (Strategy)
The system decides which elevator handles an external request.
Different buildings may use different scheduling algorithms:

  NEAREST_IDLE:    assign to closest idle elevator
  SCAN:            assign to elevator already moving in that direction
                   if none, assign to nearest idle
  ROUND_ROBIN:     distribute requests evenly across elevators
  LEAST_LOADED:    assign to elevator with fewest pending stops

The algorithm can be configured at system startup and swapped.

### Notifications (Observer)
  FloorDisplayUpdater:  updates floor indicator lights
  BuildingMonitor:      logs all activity for maintenance records
  FireSafetySystem:     overrides all elevators in emergency
  ElevatorDoorController: triggers door open/close mechanisms

---

## The Key Design Discussion: Request Queue

How does each elevator manage its list of floors to visit?

### Option A: Simple List<Integer>
  + Easy to implement
  - No inherent ordering — must sort manually
  - Hard to implement SCAN algorithm cleanly

### Option B: Two PriorityQueues (up-queue and down-queue)
  + Naturally ordered
  + SCAN: drain up-queue going up, drain down-queue going down
  + O(log n) insert, O(log n) poll
  ✓ Best for SCAN algorithm — explain this

### Option C: TreeSet<Integer>
  + Sorted, no duplicates (no double-stopping at same floor)
  + O(log n) operations
  + headSet() / tailSet() for floors above/below current
  → Very clean for SCAN — worth mentioning

WHAT TO SAY:
  "For the request queue I'm considering a TreeSet because it keeps
   floors sorted and automatically deduplicates — if two people
   on floor 5 press the button, we don't stop twice.
   For SCAN I can use tailSet(currentFloor) for floors above
   and headSet(currentFloor) for floors below."

---

## Class Design to Think About

### Enums
  ElevatorState   — IDLE, MOVING_UP, MOVING_DOWN, DOOR_OPEN, MAINTENANCE
  RequestType     — EXTERNAL, INTERNAL
  Direction       — UP, DOWN
  ElevatorType    — STANDARD, EXPRESS, FREIGHT

### Core Classes
  Floor           — floor number, list of waiting people
  Request         — floor, direction (for external), requestType, timestamp
  Elevator        — id, type, state, currentFloor, requestQueue, capacity
  ElevatorSystem  — list of elevators, scheduler, observers
  SchedulingStrategy — interface for scheduling algorithms
  ElevatorObserver   — interface for system events

### Key Method Signatures to Think About

  // ElevatorSystem
  void requestElevator(int floor, Direction direction)   // external request
  ElevatorStatus getStatus(int elevatorId)
  void setSchedulingStrategy(SchedulingStrategy strategy)
  void addObserver(ElevatorObserver observer)
  void triggerFireEmergency()                            // all elevators to ground floor

  // Elevator
  void addStop(int floor)
  void step()                   // simulate one unit of time — move one floor or open door
  boolean canAccept(Request request)
  int getCurrentFloor()
  ElevatorState getState()
  int getDistanceTo(int floor)

  // SchedulingStrategy
  Elevator selectElevator(List<Elevator> available, Request request)

  // ElevatorObserver
  void onElevatorArrived(int elevatorId, int floor)
  void onDoorsOpened(int elevatorId, int floor)
  void onDoorsClosed(int elevatorId, int floor)
  void onElevatorOutOfService(int elevatorId)

---

## What the Interviewer Is Looking For

### OOP & Architecture
- Elevator type hierarchy: abstract Elevator → StandardElevator, ExpressElevator, FreightElevator
  Or: Elevator + ElevatorType enum with type-specific constraints
- SchedulingStrategy cleanly separates scheduling from system logic
- ElevatorSystem does not hardcode any scheduling algorithm

### Efficiency Discussion
- Request queue: TreeSet vs PriorityQueue vs List — justify your choice
- SCAN algorithm: floors sorted, process in direction → O(log n) per request
- Finding best elevator: O(M) where M = number of elevators (acceptable)

### Talking Through Decisions
  "I'm making step() advance the elevator by one unit — one floor or
   door operation. This makes the system easy to simulate and test.
   The ElevatorSystem calls step() on all elevators each tick."

  "I'm separating SchedulingStrategy completely — ElevatorSystem
   just calls strategy.selectElevator(). The system doesn't know
   or care if it's SCAN or NEAREST_IDLE. This means we can tune
   the algorithm without changing any elevator logic."

---

## Example Flow

```java
ElevatorSystem system = new ElevatorSystem(10, 3);  // 10 floors, 3 elevators
system.setSchedulingStrategy(new ScanStrategy());
system.addObserver(new FloorDisplayUpdater());

// Person on floor 3 wants to go up
system.requestElevator(3, Direction.UP);
// Strategy selects best elevator, adds floor 3 to its queue

// Inside elevator, person presses floor 7
elevator.addStop(7);

// Simulate
system.step();   // all elevators advance one unit
system.step();
```

---

## Priority Guide

**Primary focus:**
- Elevator state machine and valid transitions
- Scheduling strategy design and justification
- Request queue data structure choice and justification
- Talking through every architectural decision

**Secondary focus:**
- Full SCAN algorithm implementation
- Concurrency considerations (mention: in production, elevators run in parallel)
- Edge cases: fire emergency, elevator full, maintenance mode
