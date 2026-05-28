# Delivery Tracker

## Patterns: State + Observer + Factory

## Problem Statement

Design a package delivery tracking system.
Packages move through defined states, customers and
admins are notified at each step, and different
delivery types are created based on package properties.

---

## Requirements

### Package & States
A package moves through the following states:
  CREATED → PICKED_UP → IN_TRANSIT → OUT_FOR_DELIVERY → DELIVERED
                                                       → FAILED_DELIVERY → RETURNED

Rules:
- CREATED → PICKED_UP: when courier picks up the package
- IN_TRANSIT → OUT_FOR_DELIVERY: when package reaches destination city
- OUT_FOR_DELIVERY → DELIVERED: successful delivery, requires recipient signature
- OUT_FOR_DELIVERY → FAILED_DELIVERY: nobody home, 3 attempts max
- FAILED_DELIVERY → OUT_FOR_DELIVERY: retry next day
- FAILED_DELIVERY → RETURNED: after 3 failed attempts

Each state transition records: timestamp, location, responsible courier

### Delivery Types (Factory)
Different package properties determine delivery type:
  STANDARD:  weight <= 5kg, no special handling
  EXPRESS:   requested next-day delivery (surcharge applied)
  FRAGILE:   requires careful handling, special vehicle
  OVERSIZED: weight > 30kg or dimensions exceed limits, special equipment

Creation logic:
  The system analyzes package properties and creates
  the correct delivery type — the caller does not decide.

### Notifications (Observer)
On each state change, relevant parties are notified:
  Customer:       PICKED_UP, OUT_FOR_DELIVERY, DELIVERED, FAILED_DELIVERY
  CourierManager: FAILED_DELIVERY, RETURNED
  WarehouseTeam:  RETURNED
  Analytics:      every state change (for reporting)

Notification channels differ per recipient —
  Customer gets SMS + Email, CourierManager gets internal alert.

---

## What to Think About

- Who is responsible for validating that a transition is allowed?
  (CREATED → DELIVERED directly should not be possible)
- Should the package know its own valid transitions, or
  should an external validator handle it?
- Where does the factory creation logic live?
  What inputs does it need to decide the delivery type?
- How do you handle different notification channels per observer?
  (Customer → SMS, Manager → internal) without hardcoding it in the tracker

## Patterns to Apply

STATE:
  Each DeliveryState defines which transitions are valid from it.
  Package delegates behavior to its current state.
  Invalid transitions throw IllegalStateTransitionException.

OBSERVER:
  DeliveryTracker maintains List<DeliveryObserver>.
  On each transition: notifyAll(new StateChangeEvent(package, oldState, newState)).
  CustomerNotifier, CourierManagerNotifier implement DeliveryObserver.
  Each observer decides internally how to communicate (SMS, email, alert).

FACTORY:
  DeliveryTypeFactory.create(PackageProperties properties)
  Analyzes: weight, dimensions, requestedDeliveryDate, fragileFlag.
  Returns the appropriate DeliveryType enum or config object.
  Caller (OrderService) does not contain any if/else logic.

---

## Key Method Signatures to Think About

  // Package
  void transitionTo(DeliveryState newState, Location location, String courierId)
  DeliveryState getCurrentState()
  List<StateTransitionRecord> getHistory()
  boolean canTransitionTo(DeliveryState targetState)

  // DeliveryState (interface)
  void onEnter(Package pkg)
  void onExit(Package pkg)
  Set<DeliveryState> getAllowedTransitions()

  // DeliveryTypeFactory
  static DeliveryType determine(PackageProperties properties)

  // DeliveryObserver
  void onStateChanged(StateChangeEvent event)

  // StateChangeEvent
  Package getPackage()
  DeliveryState getPreviousState()
  DeliveryState getNewState()
  LocalDateTime getTimestamp()
  Location getLocation()
