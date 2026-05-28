# Battleship Game

## Problem Statement

Design a two-player Battleship game.
Each player has a 10x10 grid on which they place ships.
Players take turns firing at coordinates on the opponent's grid.
The goal is to sink all of the opponent's ships.

---

## Game Rules

- Grid is 10x10, coordinates are (row, col) from (0,0) to (9,9)
- Each player places 5 ships before the game starts:

    CARRIER      → occupies 5 cells
    BATTLESHIP   → occupies 4 cells
    CRUISER      → occupies 3 cells
    SUBMARINE    → occupies 3 cells
    DESTROYER    → occupies 2 cells

- Ships can be placed horizontally or vertically
- Ships cannot overlap or go out of bounds
- Players alternate turns — on each turn a player fires at one coordinate
- A shot result is either HIT or MISS
- A ship is SUNK when all its cells have been hit
- The game is over when all ships of one player are sunk
- A player cannot fire at the same coordinate twice

---

## Requirements

### Setup Phase
- Each player places all 5 ships on their grid
- Placement must be validated (in bounds, no overlap, correct size)
- Once both players have placed ships, the game begins

### Gameplay Phase
- Players alternate turns
- On each turn: fire at a coordinate → receive HIT or MISS
- If a ship is sunk, the result is SUNK (not just HIT)
- A player cannot fire at an already-fired coordinate
- After each shot, the game checks if the game is over

### State & Queries
- A player can view their own grid (showing ship positions + incoming shots)
- A player can view their attack grid (showing their shots: HIT / MISS)
- The game can report whose turn it is
- The game can report the winner once the game is over

---

## The Key Design Discussion: How Do You Store the Board?

This is one of the most important parts of the interview.
Think through the options before committing to one.

### Option A: 2D char array  char[10][10]
    'E' = empty, 'S' = ship, 'H' = hit, 'M' = miss
    + Simple, O(1) access by coordinate
    - Not expressive: what does 'H' mean? Needs a legend.
    - Hard to extend: adding a new state means changing all switch-cases

### Option B: 2D enum array  Cell[10][10]
    enum Cell { EMPTY, SHIP, HIT, MISS }
    + O(1) access, self-documenting, type-safe
    + Easy to add new states without breaking existing code
    + Compiler checks all cases in switch
    - Slightly more code to set up
    ✓ BEST CHOICE for this problem — explain why during the interview

### Option C: HashMap<Coordinate, Cell>
    Map<Coordinate, Cell> grid
    + Flexible, coordinate is a first-class object
    + Natural fit if grid is sparse (most cells empty)
    - O(1) average but with more overhead than array
    - Overkill for a fixed 10x10 grid
    → Mention this option but explain why B is better here

### Option D: Two separate Sets
    Set<Coordinate> shipCoordinates
    Set<Coordinate> hitCoordinates
    + Simple to check if a coordinate is a ship or a hit
    - Less unified, harder to render the board
    - Checking cell state requires multiple set lookups
    → Mention as an alternative approach, explain tradeoffs

WHAT TO SAY:
    "I will use a 2D enum array because the grid is fixed size,
     so we get O(1) guaranteed access with no hash overhead.
     Using an enum instead of char makes the code self-documenting
     and the compiler will warn us if we miss a case in a switch."

---

## Class Design to Think About

### Enums
    ShipType      — CARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER
                    each carrying its size as data
    Cell          — EMPTY, SHIP, HIT, MISS
    Orientation   — HORIZONTAL, VERTICAL
    ShotResult    — HIT, MISS, SUNK, ALREADY_FIRED, GAME_OVER
    GameStatus    — SETUP, IN_PROGRESS, FINISHED

### Core Classes
    Coordinate    — (row, col), validates bounds, equals/hashCode
    Ship          — type, orientation, list of coordinates, tracks hits
    Board         — 10x10 grid, owns placement and shot logic
    Player        — name, owns a Board, tracks their attack history
    Game          — manages two players, turn logic, win condition

### Key Method Signatures to Think About

    // Ship
    boolean occupies(Coordinate coord)
    boolean registerHit(Coordinate coord)   // returns true if it was a new hit
    boolean isSunk()

    // Board
    boolean placeShip(Ship ship, Coordinate origin, Orientation orientation)
    ShotResult receiveShot(Coordinate coord)
    boolean allShipsSunk()
    Cell[][] getViewForOwner()              // shows own ships + received shots
    Cell[][] getViewForOpponent()           // shows only hits and misses (no ship positions)

    // Game
    ShotResult fire(Player attacker, Coordinate target)
    boolean isOver()
    Player getWinner()
    Player getCurrentPlayer()

---

## What the Interviewer Is Looking For

### Design
- Enums for all types — especially Cell and ShotResult (not String returns)
- Coordinate as a proper class, not two raw ints passed around everywhere
- Ship knows if it is sunk — not the Board, not the Game
- Board handles its own validation — not the Game
- Game orchestrates, does not do low-level work

### Efficiency Discussion (must be proactive)
- Board storage: explain why Cell[10][10] over HashMap or char[][]
- Ship lookup: how does Board find which ship was hit?
  Option 1: scan all ships O(k) where k = number of ships (acceptable, k<=5)
  Option 2: Map<Coordinate, Ship> for O(1) lookup — mention this as optimization
- Already-fired check: Set<Coordinate> of fired coordinates — O(1) lookup

### Talking Through Your Thinking (critical in this interview)
  DO NOT code in silence. Narrate every decision:
    "I'm making Coordinate a class because I'll be passing row+col
     together everywhere — it makes the API cleaner and I can add
     validation in one place."

    "I'm returning ShotResult instead of boolean from receiveShot()
     because the caller needs to know not just hit/miss but also
     whether a ship was sunk — a boolean loses that information."

    "I'm giving Board two view methods — one for the owner and one
     for the opponent — because the opponent must never see ship
     positions, only hits and misses."

---

## Example Flow

```java
Game game = new Game(player1, player2);

// Setup phase
game.placeShip(player1, ShipType.CARRIER, new Coordinate(0, 0), Orientation.HORIZONTAL);
game.placeShip(player2, ShipType.DESTROYER, new Coordinate(5, 5), Orientation.VERTICAL);

game.startGame();

// Gameplay
ShotResult result = game.fire(player1, new Coordinate(5, 5));  // HIT
result = game.fire(player1, new Coordinate(6, 5));             // SUNK
result = game.fire(player2, new Coordinate(0, 0));             // HIT

game.isOver();         // false
game.getCurrentPlayer(); // player1 or player2
```

---

## Priority Guide

**Primary focus:**
- OOP structure — clear responsibilities per class
- Enums everywhere appropriate
- Explaining your choices out loud as you design
- Efficiency discussion for board storage and lookups

**Secondary focus:**
- Full implementation of methods
- Perfect edge case coverage (mention them, don't necessarily implement all)
