# Game Character System

## Patterns: State + Decorator + Strategy

## Problem Statement

Design a character system for an action game.
Characters have states that change their behavior,
can be dynamically enhanced with power-ups,
and use interchangeable attack strategies.

---

## Requirements

### Character States
A character can be in one of these states:
  IDLE, MOVING, ATTACKING, DEFENDING, STUNNED, DEAD

Rules:
- STUNNED: character cannot attack or move for N seconds
- DEAD: character cannot perform any action
- DEFENDING: character takes reduced damage, cannot attack
- ATTACKING: character cannot defend simultaneously
- Any state → DEAD: if health drops to 0

Each state defines which actions are allowed and which are blocked.

### Power-Ups (Decorator)
Power-ups enhance a character's stats dynamically:
  SHIELD:    reduces incoming damage by 30%
  FIRE:      adds burn damage to each attack (+10 dmg/sec for 5 sec)
  SPEED:     increases movement speed by 50%
  INVISIBLE: cannot be targeted by enemies
  DOUBLE_HP: doubles current max health

Rules:
- Multiple power-ups can be active at the same time
- Each power-up has a duration and expires after it ends
- Power-ups stack: SHIELD + FIRE + SPEED all active simultaneously
- A power-up wraps the character — does not modify the character class

### Attack Strategies (Strategy)
Characters can switch attack style at runtime:
  MELEE:  close-range, high damage, slow speed (sword)
  RANGED: long-range, medium damage, medium speed (bow)
  MAGIC:  area-of-effect, variable damage, high mana cost (staff)
  STEALTH: high damage only if enemy is unaware, otherwise low

Each strategy has different:
  - Damage calculation
  - Range
  - Cooldown
  - Resource cost (health, mana, stamina)

---

## What to Think About

- What happens when a power-up expires?
  Who is responsible for removing it — the power-up itself,
  a timer, or the character?
- How do you calculate total damage when 3 power-ups
  are stacked on top of each other?
  (hint: each decorator modifies the value from the one below it)
- When a character is STUNNED, who blocks the attack call?
  The state or the character?
- Should the character know about its attack strategy,
  or should it just call strategy.execute()?
- If DEFENDING state blocks attacking, and a FIRE power-up
  automatically adds burn damage — does burn still apply while defending?
  (think about interaction between State and Decorator)

## Patterns to Apply

STATE:
  CharacterState interface with methods: attack(), defend(), move(), takeDamage().
  Each state implements what is allowed and what throws/ignores.
  Character delegates all actions to currentState.

  IdleState    → can do anything
  StunnedState → attack() and move() do nothing / throw
  DeadState    → all actions throw CharacterDeadException
  DefendingState → attack() blocked, takeDamage() reduced

DECORATOR:
  Character implements a CharacterStats interface (getDamage(), getHealth(), etc.)
  Each PowerUp wraps a CharacterStats and modifies specific values.

  class FirePowerUp implements CharacterStats {
      private final CharacterStats wrapped;
      int getDamage() { return wrapped.getDamage() + 10; }
      // other stats delegated to wrapped
  }

  Stack of power-ups:
  CharacterStats enhanced = new ShieldPowerUp(new FirePowerUp(new SpeedPowerUp(base)));

STRATEGY:
  AttackStrategy interface with execute(Character attacker, Character target).
  MeleeStrategy, RangedStrategy, MagicStrategy, StealthStrategy implement it.
  Character holds a reference to its current strategy.
  character.switchStrategy(new MagicStrategy()) changes it at runtime.

---

## Key Method Signatures to Think About

  // Character
  AttackResult attack(Character target)
  void defend()
  void move(Direction direction)
  void takeDamage(int amount)
  void applyPowerUp(PowerUp powerUp)
  void switchAttackStrategy(AttackStrategy strategy)
  boolean isAlive()
  CharacterStats getEffectiveStats()    // stats after all power-ups applied

  // CharacterState
  AttackResult onAttack(Character character, Character target)
  void onTakeDamage(Character character, int amount)
  boolean canAct()

  // AttackStrategy
  AttackResult execute(CharacterStats attacker, CharacterStats target)
  int getRange()
  int getCooldownMs()
  ResourceCost getResourceCost()

  // PowerUp (Decorator)
  int getDamage()
  int getMaxHealth()
  float getSpeedMultiplier()
  boolean isExpired()
