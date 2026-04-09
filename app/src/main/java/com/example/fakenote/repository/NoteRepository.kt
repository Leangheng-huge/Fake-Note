package com.example.fakenote.repository

import com.example.fakenote.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteRepository {

    private var nextId = 4

    private val _notes = MutableStateFlow(
        mutableListOf(
            Note(1, "Meeting Notes", "Discuss Q2 roadmap with the team. Follow up on design specs.", "Apr 9, 2026"),
            Note(2, "Grocery List", "Milk, Eggs, Bread, Avocados, Pasta, Olive Oil.", "Apr 8, 2026"),
            Note(3, "Book Ideas", "Start reading 'Deep Work' by Cal Newport. Take notes on chapter 3.", "Apr 7, 2026")
        )
    )
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(title: String, content: String) {
        val today = "Apr 9, 2026"
        val newNote = Note(id = nextId++, title = title, content = content, timestamp = today)
        _notes.value = (_notes.value + newNote).toMutableList()
    }

    fun deleteNote(id: Int) {
        _notes.value = _notes.value.filter { it.id != id }.toMutableList()
    }

    fun updateNote(id: Int, title: String, content: String) {
        _notes.value = _notes.value.map {
            if (it.id == id) it.copy(title = title, content = content, timestamp = "Apr 9, 2026")
            else it
        }.toMutableList()
    }
}
