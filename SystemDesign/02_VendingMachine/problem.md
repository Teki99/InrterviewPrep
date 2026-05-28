# Vending Machine

## Patterns: State + Strategy + Observer

## Problem Statement

Design a vending machine that sells products,
accepts multiple payment methods, and notifies
administrators when stock runs low.

---

## Requirements

### Products & Stock
- The machine holds multiple product slots, each with a product and quantity
- Each product has a name, price, and stock count
- When a product reaches low stock (configurable threshold), someone must be notified

### Machine States
The machine behaves differently depending on its current state:
- IDLE: waiting for a customer, accepts product selection
- PRODUCT_SELECTED: product chosen, waiting for payment
- PROCESSING_PAYMENT: payment in progress
- DISPENSING: dispensing the product
- OUT_OF_STOCK: selected product unavailable
- MAINTENANCE: machine is offline

Rules:
- Payment cannot be accepted if no product is selected
- A product cannot be dispensed before payment is complete
- The machine returns to IDLE after dispensing or cancellation

### Payment
- Supported methods: COIN, CARD, MOBILE
- Each method has different validation logic:
    COIN:   counts total inserted coin value
    CARD:   contacts external terminal (simulate with boolean)
    MOBILE: QR code scan confirmation
- If payment is insufficient, the machine waits for more
- Payment can be cancelled at any time before confirmation — refund issued

### Notifications
- When stock of any product drops below threshold → notify StockManager
- When payment fails → notify MaintenanceTeam
- When machine enters MAINTENANCE state → notify MaintenanceTeam

---

## What to Think About

- What transitions are valid between states?
  (IDLE → PRODUCT_SELECTED is valid, IDLE → DISPENSING is not)
- Where does the state transition logic live?
  In the machine? In each state? Think about State pattern.
- How does the machine process payment without knowing
  which payment method is being used?
- Who notifies StockManager — the machine or the product slot?

## Patterns to Apply

STATE:
  Each state is a class that implements a common interface.
  The machine delegates behavior to the current state.
  Invalid actions in a given state throw or return an error.

STRATEGY:
  PaymentMethod is an interface.
  CoinPayment, CardPayment, MobilePayment each implement it.
  The machine calls payment.process() — it does not care HOW.

OBSERVER:
  VendingMachine maintains a list of VendingObserver subscribers.
  Events: LOW_STOCK, PAYMENT_FAILED, MAINTENANCE_REQUIRED.
  StockManager and MaintenanceTeam implement VendingObserver.

---

## Key Method Signatures to Think About

  // Machine
  void selectProduct(String productCode)
  PaymentResult processPayment(PaymentMethod method)
  void cancel()
  void restock(String productCode, int quantity)  // admin

  // State interface
  void onProductSelected(VendingMachine machine, String code)
  PaymentResult onPaymentReceived(VendingMachine machine, PaymentMethod method)
  void onCancel(VendingMachine machine)

  // PaymentMethod (Strategy)
  PaymentResult process(BigDecimal amount)
  boolean refund(BigDecimal amount)

  // VendingObserver
  void onLowStock(String productCode, int remainingQuantity)
  void onPaymentFailed(String reason)
  void onMaintenanceRequired()
