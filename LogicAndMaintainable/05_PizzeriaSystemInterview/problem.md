# Pizzeria System

## Problem Statement

Design a system for managing orders in a pizzeria.
The system should support order creation, item management,
multiple payment methods, and receipt generation.

---

## Functional Requirements

### Pizza
- A pizza has a size (small, medium, large, extra large)
- A pizza has a crust type (thin, thick, stuffed, gluten-free)
- Toppings can be added to a pizza
- Each topping has its own price
- The total pizza price = base price (size + crust) + sum of topping prices

### Order
- A customer can order one or more pizzas
- An order has a status that changes over time:
  (received → in preparation → ready → delivered)
- Items can be added or removed only while the order is in "received" status
- An order can be for pickup or delivery

### Payment
- Supported payment methods: cash, card, voucher, online
- A voucher can cover part or the full amount
- If a voucher does not cover the full amount, the remainder is paid by another method
- The system records which payment method(s) were used

### Receipt
- A receipt is generated after payment is completed
- It contains: all items with prices, applied discounts,
  payment method(s), total amount, date and time

---

## Technical Notes

- Use enums wherever it makes sense (do not use Strings for types or statuses)
- Think about which class is responsible for what
  (e.g. who creates the receipt? who calculates the total price?)
- Method names should be descriptive enough that someone unfamiliar with the code
  understands what they do just by reading the signature
- Use inheritance only where it genuinely makes sense — not by force
- Think about what is public API vs internal logic

---

## Expected Deliverable

A class design with:
- Enums for all relevant types
- Classes with fields and methods (NO implementation — signature + description only)
- Clearly indicated inheritance / interfaces where applicable
- A short comment on each method describing what it does

---

## Example Flow

```
1. Customer opens a new order
2. Adds a pizza: large, thin crust, mozzarella + pepperoni + olives
3. Adds another pizza: medium, stuffed crust, cheese only
4. Order moves to "in preparation" — no more changes allowed
5. Order is ready — status changes to "ready"
6. Customer pays: 500 RSD by voucher + remainder by card
7. System generates a receipt
```

---

## What the Interviewer Is Looking For

- Are enums used instead of magic strings?
- Are methods placed on the right classes? (separation of concerns)
- Are method names self-explanatory without needing comments?
  (addTopping() vs add(), calculateTotal() vs getPrice())
- Is there a logical class hierarchy?
- Is the API intuitive — can someone use the system without reading the implementation?
- Are edge cases considered? (voucher exceeds total amount, modifying an order in wrong status)


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
