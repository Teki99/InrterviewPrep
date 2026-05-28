# Logger Framework

## Patterns: Singleton + Strategy + Decorator

## Problem Statement

Design a logging framework that can write logs
to multiple destinations, enrich log entries with
metadata, and ensure a single consistent logger
is used across the entire application.

---

## Requirements

### Log Levels
- Supported levels: DEBUG, INFO, WARNING, ERROR, CRITICAL
- Logger can be configured with a minimum level —
  messages below that level are ignored
- Example: if min level is WARNING, DEBUG and INFO are silently dropped

### Log Destinations (Strategy)
- Logs can be written to: CONSOLE, FILE, DATABASE, REMOTE (HTTP endpoint)
- Each destination handles writing differently
- Multiple destinations can be active at the same time
- Destinations can be added or removed at runtime

### Log Enrichment (Decorator)
- A raw log entry has: level, message, timestamp
- Enrichers can add extra information to the entry:
    ThreadEnricher:     adds current thread name
    ContextEnricher:    adds user ID / session ID
    StackTraceEnricher: adds stack trace (for ERROR and above)
    JsonEnricher:       formats the entire entry as JSON
- Enrichers are stackable — you can apply multiple in order
- The order of enrichers matters

### Single Instance (Singleton)
- Only one Logger instance should exist in the application
- All parts of the app use the same logger with the same config
- Logger is initialized once at startup with its destinations and enrichers

---

## What to Think About

- How do you add a new destination without touching the Logger class?
- What is the difference between a "destination" and an "enricher"?
  (one decides WHERE to write, one decides WHAT to write)
- Where is minimum level filtering applied — in Logger or in each destination?
- How do you ensure Singleton is thread-safe?
  (mention even if you don't implement it)
- What happens if a FILE destination fails to write — does it crash the app?

## Patterns to Apply

SINGLETON:
  Logger has a private constructor and a static getInstance() method.
  All callers use Logger.getInstance().log(...).

STRATEGY:
  LogDestination is an interface with a write(LogEntry entry) method.
  ConsoleDestination, FileDestination, DatabaseDestination implement it.
  Logger holds a List<LogDestination> and writes to all of them.

DECORATOR:
  LogEntry starts as a basic object (level, message, timestamp).
  Each LogEnricher wraps it and adds more data.
  Logger passes the entry through the enricher chain before writing.

  interface LogEnricher {
      LogEntry enrich(LogEntry entry);
  }
  // each enricher calls the next one and adds its data

---

## Key Method Signatures to Think About

  // Logger (Singleton)
  static Logger getInstance()
  void log(LogLevel level, String message)
  void addDestination(LogDestination destination)
  void removeDestination(LogDestination destination)
  void addEnricher(LogEnricher enricher)
  void setMinimumLevel(LogLevel level)

  // LogDestination (Strategy)
  void write(LogEntry entry)
  boolean isAvailable()   // can this destination currently accept logs?

  // LogEnricher (Decorator)
  LogEntry enrich(LogEntry entry)

  // LogEntry
  LogEntry withThreadName(String thread)
  LogEntry withContext(String key, String value)
  LogEntry withStackTrace(String stackTrace)
  // LogEntry should be immutable — each with() returns a new instance
