# Cinema Booking System

## Difficulty: LARGE
## Focus: Full system design — all concepts combined

## Problem Statement

Design a cinema booking system.
The system should support movie scheduling, seat reservations,
ticket purchasing with different pricing tiers, and booking management.

## Requirements

### Movies & Screenings
- A movie has: title, genre, duration (minutes), age rating
- Genre options: ACTION, COMEDY, DRAMA, HORROR, ANIMATION, DOCUMENTARY
- Age rating options: G, PG, PG_13, R, NC_17
- A screening is a specific showing of a movie at a date/time in a specific hall
- A hall has a fixed number of seats arranged in rows

### Seats & Reservations
- Seat types: STANDARD, PREMIUM, VIP, WHEELCHAIR_ACCESSIBLE
- Each seat type has a different base price
- A seat can be: AVAILABLE, RESERVED, SOLD, UNAVAILABLE (e.g. broken)
- A customer can reserve one or more seats for a screening
- A reservation expires if not paid within 15 minutes
- After payment, a reservation becomes a confirmed booking

### Tickets & Pricing
- Ticket categories with discounts applied to base seat price:
    ADULT:   no discount
    STUDENT: 20% off
    SENIOR:  25% off
    CHILD:   40% off
- A booking contains one or more tickets (one per seat)
- Each ticket specifies: seat, movie, screening time, ticket category, final price

### Payments & Receipts
- Payment methods: CASH, CARD, GIFT_CARD
- A gift card can partially cover the total; the rest is paid by another method
- Receipt contains: all tickets, payment breakdown, booking reference, timestamp

## Example Usage

```java
Movie movie = new Movie("Inception", Genre.ACTION, 148, AgeRating.PG_13);

Screening screening = new Screening(movie, hall, LocalDateTime.of(2025, 6, 1, 20, 0));

Reservation reservation = bookingService.reserve(screening, List.of(seatA5, seatA6));

Ticket ticketA5 = new Ticket(seatA5, TicketCategory.ADULT);
Ticket ticketA6 = new Ticket(seatA6, TicketCategory.STUDENT);

Receipt receipt = bookingService.confirmAndPay(reservation, List.of(ticketA5, ticketA6), payment);
receipt.getBookingReference();   // "BK-20250601-0042"
receipt.getTotalAmount();        // calculated with discounts
```

## What to think about

- What is the difference between a Reservation and a Booking?
- Where does seat availability logic live — in Seat, Hall, Screening, or BookingService?
- Who is responsible for calculating the final ticket price?
- How do you handle reservation expiry? (hint: think about status + timestamp)
- Should BookingService know about payment details, or should that be separate?
- What is the public API of BookingService vs internal details?

## What the Interviewer Is Looking For

- All types modeled as enums: Genre, AgeRating, SeatType, SeatStatus,
  TicketCategory, PaymentMethod, BookingStatus
- Clear class responsibilities — no God class that does everything
- Reservation and Booking as separate concepts (reservation = tentative, booking = confirmed)
- Ticket price calculation isolated from booking logic
- BookingService as the main entry point — clean, intuitive public API
- Expiry logic considered (not necessarily implemented, but designed)
- Edge cases: reserving an already-taken seat, paying expired reservation,
  gift card exceeding total amount


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
