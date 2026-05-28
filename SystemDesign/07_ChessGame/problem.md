# Chess Game

## Difficulty: LARGE (similar scope to Battleship)
## Patterns: State + Strategy + Factory + Observer

## Problem Statement

Design a two-player chess game.
The system should handle piece movement, turn management,
move validation, check/checkmate detection, and game state tracking.

---

## Requirements

### Board & Pieces
- Standard 8x8 chess board
- 6 piece types: KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
- 2 colors: WHITE, BLACK
- Starting position is the standard chess setup
- Each piece type has its own movement rules

### Movement Rules (per piece)
  PAWN:    moves forward 1 (or 2 from starting position),
           captures diagonally, en passant, promotion at last rank
  ROOK:    moves any number of squares horizontally or vertically
  BISHOP:  moves any number of squares diagonally
  KNIGHT:  moves in L-shape, only piece that can jump over others
  QUEEN:   combines Rook + Bishop movement
  KING:    moves 1 square in any direction, castling with Rook

### Turn & Game Logic
- Players alternate turns — WHITE always moves first
- A move is valid only if:
    1. The piece belongs to the current player
    2. The move follows that piece's movement rules
    3. The path is clear (except Knight)
    4. The move does not leave the player's own King in check
- If a player has no valid moves and is in check → CHECKMATE (game over)
- If a player has no valid moves but is not in check → STALEMATE (draw)

### Game States
  SETUP → IN_PROGRESS → CHECK → CHECKMATE / STALEMATE / DRAW / RESIGNED

---

## The Key Design Discussion: How Do You Store the Board?

Same question as Battleship — think it through out loud.

### Option A: Piece[8][8]
  Direct 2D array of Piece objects (null = empty square)
  + O(1) access by position
  + Simple to render the board
  - null checks everywhere
  ✓ Good choice — explain why

### Option B: Map<Position, Piece>
  + No null handling — absence of key = empty square
  + Position is a proper object
  - Slightly more overhead than array
  → Valid alternative, mention the tradeoff

### Option C: List<Piece> with position inside each piece
  + Each piece knows where it is
  - Finding "what is at position X?" requires scanning all pieces O(n)
  ✗ Worse option — mention and reject with explanation

WHAT TO SAY:
  "I'll use Piece[8][8] for O(1) access. null means empty square.
   I considered Map<Position, Piece> to avoid nulls but the fixed 8x8
   grid makes the array simpler. I'll use Optional<Piece> in the API
   to make null handling explicit."

---

## Class Design to Think About

### Enums
  PieceType    — KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
  PieceColor   — WHITE, BLACK
  GameStatus   — SETUP, IN_PROGRESS, CHECK, CHECKMATE, STALEMATE, DRAW, RESIGNED
  MoveType     — STANDARD, CAPTURE, CASTLING, EN_PASSANT, PROMOTION

### Core Classes
  Position     — (row, col), validates 0-7 bounds
  Piece        — type, color, hasMoved (for castling/pawn double move)
  Board        — 8x8 grid, piece placement, path checking
  Move         — from, to, piece, moveType, optional captured piece
  MoveValidator — validates if a move is legal for a given board state
  Game         — players, board, turn logic, status, move history
  MoveHistory  — ordered list of all moves (enables undo, replay)

### Key Method Signatures to Think About

  // Piece (or subclass per piece type)
  List<Position> getCandidateMoves(Position from, Board board)
  // returns positions this piece COULD move to (ignoring check)

  // MoveValidator
  boolean isLegal(Move move, Board board)
  boolean wouldLeaveKingInCheck(Move move, Board board)
  List<Move> getAllLegalMoves(PieceColor color, Board board)

  // Board
  Optional<Piece> getPieceAt(Position position)
  Board apply(Move move)          // returns NEW board after move (immutable)
  boolean isPositionUnderAttack(Position pos, PieceColor byColor)

  // Game
  MoveResult makeMove(Position from, Position to)
  List<Move> getLegalMovesFor(Position position)
  boolean isInCheck(PieceColor color)
  boolean isCheckmate(PieceColor color)
  GameStatus getStatus()

---

## What the Interviewer Is Looking For

### OOP Design
- Piece hierarchy: abstract Piece → King, Queen, Rook, Bishop, Knight, Pawn
  Each subclass implements getCandidateMoves()
- Or: Piece + PieceType enum with a MoveStrategy per type (Strategy pattern)
  Both approaches are valid — be ready to argue for your choice
- MoveValidator separate from Board and Piece — single responsibility

### Efficiency Discussion
- Board storage: Piece[][] vs Map<Position, Piece>
- Finding all legal moves: must check all pieces of a color O(16 * avg_moves)
- Check detection: after every move, verify king position not under attack
- Consider: immutable Board (apply() returns new Board) makes
  check validation easier — can simulate a move without modifying state

### Talking Through Decisions
  "I'm making getCandidateMoves() return moves without checking for check,
   and then MoveValidator filters out moves that would leave the King exposed.
   This way each piece only knows its own movement rules, and check logic
   is centralized in one place."

  "I'm considering making Board immutable — apply(move) returns a new Board.
   This makes it easy to simulate 'what if I make this move' for check detection
   without having to undo the move afterwards."

---

## Example Flow

```java
Game game = new Game();
game.start();

// White moves pawn
MoveResult result = game.makeMove(new Position(6, 4), new Position(4, 4));
result.getMoveType();    // STANDARD
result.getGameStatus();  // IN_PROGRESS

// Get all legal moves for a piece
List<Move> moves = game.getLegalMovesFor(new Position(7, 1));  // Knight

game.isInCheck(PieceColor.BLACK);  // false
game.getStatus();                   // IN_PROGRESS
```

---

## Priority Guide

**Primary focus:**
- Piece hierarchy and movement rules design
- Board storage discussion and justification
- Check/checkmate detection architecture
- Talking through every design decision

**Secondary focus:**
- Full implementation of movement rules
- Edge cases: en passant, promotion, castling (mention, don't necessarily implement)
