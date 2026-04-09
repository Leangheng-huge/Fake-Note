# Fake Note

A clean, minimal Android notes app built as an MVVM architecture learning project.

![Image](https://github.com/user-attachments/assets/fc6489cb-cb8f-4c64-ac21-c61cf7292567)

## Features

- View, add, edit, and delete notes
- Clean card-based UI with a warm color palette
- Empty state screen when no notes exist
- Built entirely with Jetpack Compose

## Architecture - MVVM

```
com.example.fakenote
├── model/
│   └── Note.kt             # Data class
├── repository/
│   └── NoteRepository.kt   # Data source & business logic
├── viewmodel/
│   └── NoteViewModel.kt    # UI state management
├── ui/
│   └── NotesScreen.kt      # Composable UI
└── MainActivity.kt         # Entry point
```

**Model** -> defines the `Note` data structure  
**Repository** -> manages the notes list (add, edit, delete)  
**ViewModel** -> exposes state to the UI via `StateFlow`  
**View** -> Composable functions that react to state changes

## Tech Stack

| Tool | Purpose |
|------|---------|
| Kotlin | Primary language |
| Jetpack Compose | UI framework |
| ViewModel | UI state holder |
| StateFlow | Reactive state management |
| Material 3 | Design system |

## Getting Started

1. Clone the repo
2. Open in Android Studio
3. Run on emulator or physical device (min SDK 26)

## What I Learned

- How to separate concerns using the MVVM pattern
- Managing UI state with StateFlow and collectAsState()
- Building reactive UIs with Jetpack Compose
- How ViewModel survives configuration changes
- Passing data between layers without tight coupling

---

*Built for learning purposes*
