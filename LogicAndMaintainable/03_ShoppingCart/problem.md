# Shopping Cart

## Difficulty: MEDIUM
## Focus: Separation of concerns, naming, discount logic, API clarity

## Problem Statement

Design a shopping cart system for an online store.
The cart should support adding/removing items,
applying discounts, and generating an order summary.

## Requirements

### Cart
- A customer can add a product to the cart with a quantity
- A customer can remove a product or reduce its quantity
- The cart can return a list of all current items
- The cart can be cleared entirely

### Pricing & Discounts
- Each product has a base price and a category (ELECTRONICS, CLOTHING, FOOD, BOOKS)
- Discounts can be applied to the cart:
    - Percentage discount (e.g. 10% off everything)
    - Fixed amount discount (e.g. 200 RSD off)
    - Category discount (e.g. 15% off all CLOTHING items)
- Multiple discounts can be active at the same time
- The cart can calculate the total before and after discounts

### Order Summary
- Before checkout, the cart produces an order summary
- The summary contains: items, quantities, unit prices,
  applied discounts per item, and the final total
- Checkout is only possible if the cart is not empty

## Example Usage

```java
Cart cart = new Cart();
cart.addItem(product, 2);
cart.addItem(anotherProduct, 1);

cart.applyDiscount(new PercentageDiscount(10));
cart.applyDiscount(new CategoryDiscount(Category.CLOTHING, 15));

cart.calculateTotal();           // total after discounts
cart.generateSummary();          // full breakdown
cart.checkout();                 // moves to order, clears cart
```

## What to think about

- Should Cart know how to calculate discounts, or should that be a separate concern?
- How do you model different discount types cleanly?
  (hint: think about a common interface)
- Where does generateSummary() live — on Cart or somewhere else?
- What happens if you call checkout() on an empty cart?
- Is CartItem a separate class, or do you just store a Product + quantity?

## What the Interviewer Is Looking For

- Discount as an interface/abstract class — not a big if/else in Cart
- CartItem as a separate class (Product + quantity + calculated price)
- Cart does not know about discount implementation details
- Clear method names: addItem() vs add(), applyDiscount() vs setDiscount()
- Edge cases handled: empty cart checkout, removing item not in cart,
  applying the same discount twice
- Category as an enum


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
