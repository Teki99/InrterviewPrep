# Notification Service

## Difficulty: EASY-MEDIUM
## Focus: Interfaces, inheritance, polymorphism, method naming

## Problem Statement

Design a notification service that can send messages
through different channels: Email, SMS, and Push notification.

Each channel has different capabilities and required fields,
but all share a common concept of "sending a notification to a user".

## Requirements

- The system supports three notification channels: EMAIL, SMS, PUSH
- Every notification has: a recipient, a message body, and a priority (LOW, NORMAL, HIGH, URGENT)
- Email notifications additionally have: a subject line and optional attachments
- SMS notifications have a character limit (160 chars); longer messages must be split
- Push notifications have: a title, a deep-link URL (optional), and an expiry time
- The service can send a notification through any channel
- URGENT notifications must be logged regardless of channel
- A notification can be marked as sent, and queried for its delivery status

## Example Usage

```java
Notification email = new EmailNotification(recipient, "Your order is ready", Priority.NORMAL);
email.setSubject("Order #1234 Update");

Notification sms = new SmsNotification(recipient, "Your code is 4821", Priority.URGENT);

NotificationService service = new NotificationService();
service.send(email);
service.send(sms);

email.isSent();          // true
email.getDeliveryStatus(); // DELIVERED
```

## What to think about

- What is common to ALL notifications? → goes in a base class or interface
- What is specific to each channel? → goes in the subclass
- Should Notification be an interface or an abstract class? Why?
- Where does the "send" logic live — in the Notification or in the Service?
- How do you model DeliveryStatus? (hint: not a boolean)

## What the Interviewer Is Looking For

- Clean interface or abstract class for Notification
- Each subclass only holds what is specific to it
- Priority and DeliveryStatus as enums (not Strings)
- Method names that read like sentences: send(), isSent(), markAsDelivered()
- The service works with the base type — not with specific subclasses
  (polymorphism: service.send(Notification n), not service.sendEmail(...))
- Clear answer to: interface vs abstract class and why


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
