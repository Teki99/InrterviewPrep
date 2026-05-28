# Parking Lot System

## Difficulty: MEDIUM
## Focus: API design, multiple types, inheritance, real-world modeling

## Problem Statement

Design a parking lot management system.
The lot has multiple levels, each with a different number of spots.
Spots differ by type and the lot should match vehicles to appropriate spots.

## Requirements

### Vehicles & Spots
- Vehicle types: MOTORCYCLE, CAR, TRUCK
- Spot types: SMALL, MEDIUM, LARGE
- Matching rules:
    MOTORCYCLE → fits in SMALL, MEDIUM, or LARGE
    CAR        → fits in MEDIUM or LARGE
    TRUCK      → fits only in LARGE
- Each spot can hold exactly one vehicle at a time

### Parking Lot
- The lot has one or more levels, each with a fixed number of spots per type
- A vehicle can enter the lot if a compatible spot is available
- When a vehicle enters, it is assigned to a specific spot
- When a vehicle exits, its spot is freed
- The lot can report how many spots are available per type
- The lot can report whether a specific vehicle is currently parked

### Ticket
- When a vehicle enters, a ticket is issued
- The ticket records: vehicle info, assigned spot, entry time
- When a vehicle exits, the ticket is closed with exit time
- The fee is calculated based on time parked and vehicle type:
    MOTORCYCLE: 50 RSD/hour
    CAR:        100 RSD/hour
    TRUCK:      200 RSD/hour

## Example Usage

```java
ParkingLot lot = new ParkingLot(3);   // 3 levels
lot.addLevel(new Level(10, 20, 5));   // 10 small, 20 medium, 5 large spots

Ticket ticket = lot.enter(new Car("BG-123-AB"));
// ... time passes ...
Receipt receipt = lot.exit(ticket);
receipt.getAmountDue();   // calculated fee
```

## What to think about

- Should Vehicle be an abstract class or an interface?
- Where does the spot-matching logic live?
  (in Vehicle? in Spot? in Level? in ParkingLot?)
- How does the lot find the best available spot? (first available? smallest fit?)
- What happens when lot.exit() is called with an invalid ticket?
- Should Level be exposed in the public API, or is it an internal detail?

## What the Interviewer Is Looking For

- VehicleType and SpotType as enums
- Vehicle hierarchy: abstract Vehicle → Car, Motorcycle, Truck
- Each vehicle knows which spot types it can fit in
- ParkingLot has a clean public API — caller does not deal with levels or spots directly
- Ticket as a proper class, not just an ID
- Fee calculation separated from parking logic (not inside ParkingLot)
- Clear edge case handling: lot full, vehicle not found on exit


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
