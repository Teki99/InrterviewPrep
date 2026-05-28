# Text Editor

## Patterns: Command + Observer + Decorator

## Problem Statement

Design the core of a text editor that supports
undo/redo, text formatting, and automatic reactions
to document changes (autosave, word count, spell check).

---

## Requirements

### Document & Editing
- A document holds a string of text
- Supported operations: insert text, delete text, replace text
- Each operation can be undone and redone (unlimited history)
- Undo reverses the last operation, Redo re-applies it

### Text Formatting (Decorator)
- Text can be rendered with formatting applied on top:
    Bold, Italic, Underline, Strikethrough
- Multiple formats can be combined on the same text
- Formatting does not change the underlying document —
  it only affects how text is displayed/rendered

### Reactions to Changes (Observer)
- When the document changes, multiple components react:
    AutoSave:    saves document to disk after every N changes
    WordCounter: updates word and character count
    SpellCheck:  flags potentially misspelled words
    ChangeLog:   records a history of all changes with timestamps
- Each component reacts independently
- New components can be added without changing the editor

---

## What to Think About

- How does undo know what to reverse?
  (hint: each operation must store enough info to reverse itself)
- Is the undo history a stack? What happens to redo history
  when a new operation is performed after an undo?
- Does formatting change the Document, or does it wrap it?
- Who triggers observer notifications — Document or Editor?
- What if an observer (e.g. AutoSave) fails — should it
  block other observers from running?

## Patterns to Apply

COMMAND:
  Each editing operation is an object implementing a Command interface.
  Commands have execute() and undo() methods.
  Editor maintains an undo stack and a redo stack.
  When a new command is executed, the redo stack is cleared.

  interface EditCommand {
      void execute(Document document)
      void undo(Document document)
  }

  class InsertTextCommand implements EditCommand { ... }
  class DeleteTextCommand  implements EditCommand { ... }
  class ReplaceTextCommand implements EditCommand { ... }

OBSERVER:
  Document (or Editor) maintains a list of DocumentObserver listeners.
  After every change, all observers are notified.
  Each observer reacts in its own way.

  interface DocumentObserver {
      void onDocumentChanged(DocumentChangeEvent event)
  }

DECORATOR:
  TextRenderer is an interface with render(String text) method.
  PlainTextRenderer is the base implementation.
  BoldDecorator, ItalicDecorator, UnderlineDecorator each wrap a renderer.
  They can be stacked: new BoldDecorator(new ItalicDecorator(base))

---

## Key Method Signatures to Think About

  // Editor
  void execute(EditCommand command)   // executes and pushes to undo stack
  void undo()
  void redo()
  boolean canUndo()
  boolean canRedo()
  void addObserver(DocumentObserver observer)

  // EditCommand
  void execute(Document document)
  void undo(Document document)
  String getDescription()   // e.g. "Insert 'hello' at position 5"

  // Document
  void insert(int position, String text)
  void delete(int from, int to)
  String getContent()

  // TextRenderer (Decorator)
  String render(String text)
