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



