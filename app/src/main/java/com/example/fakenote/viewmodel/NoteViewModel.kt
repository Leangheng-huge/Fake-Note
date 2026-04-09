package com.example.fakenote.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fakenote.model.Note
import com.example.fakenote.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteViewModel(
    private val repository: NoteRepository = NoteRepository()
) : ViewModel() {

    // Expose notes list from repository
    val notes: StateFlow<List<Note>> = repository.notes

    // UI state for the add/edit dialog
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    // Track which note is being edited (null = adding new)
    private val _editingNote = MutableStateFlow<Note?>(null)
    val editingNote: StateFlow<Note?> = _editingNote.asStateFlow()

    // Form field state
    private val _titleInput = MutableStateFlow("")
    val titleInput: StateFlow<String> = _titleInput.asStateFlow()

    private val _contentInput = MutableStateFlow("")
    val contentInput: StateFlow<String> = _contentInput.asStateFlow()

   //action to start

    fun onTitleChange(value: String) { _titleInput.value = value }
    fun onContentChange(value: String) { _contentInput.value = value }

    fun openAddDialog() {
        _editingNote.value = null
        _titleInput.value = ""
        _contentInput.value = ""
        _showDialog.value = true
    }

    fun openEditDialog(note: Note) {
        _editingNote.value = note
        _titleInput.value = note.title
        _contentInput.value = note.content
        _showDialog.value = true
    }

    fun dismissDialog() {
        _showDialog.value = false
        _editingNote.value = null
    }

    fun saveNote() {
        val title = _titleInput.value.trim()
        val content = _contentInput.value.trim()
        if (title.isBlank()) return

        val editing = _editingNote.value
        if (editing != null) {
            repository.updateNote(editing.id, title, content)
        } else {
            repository.addNote(title, content)
        }
        dismissDialog()
    }

    fun deleteNote(id: Int) {
        repository.deleteNote(id)
    }
}