package com.example.fakenote.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fakenote.model.Note
import com.example.fakenote.viewmodel.NoteViewModel

// ── Color Palette ────────────────────────────────────────────────
private val Background = Color(0xFFF5F0EB)
private val Surface    = Color(0xFFFFFFFF)
private val Primary    = Color(0xFF2D2D2D)
private val Accent     = Color(0xFFE8642C)
private val Muted      = Color(0xFF9A9A9A)

// ── Root Screen ──────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: NoteViewModel = viewModel()) {
    val notes       by viewModel.notes.collectAsState()
    val showDialog  by viewModel.showDialog.collectAsState()
    val editingNote by viewModel.editingNote.collectAsState()
    val title       by viewModel.titleInput.collectAsState()
    val content     by viewModel.contentInput.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // top bar
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "My Notes",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                        Text(
                            "${notes.size} notes",
                            fontSize = 12.sp,
                            color = Muted
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )

            // note list
            if (notes.isEmpty()) {
                EmptyState(modifier = Modifier.fillMaxSize())
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(notes, key = { it.id }) { note ->
                        NoteCard(
                            note = note,
                            onEdit = { viewModel.openEditDialog(note) },
                            onDelete = { viewModel.deleteNote(note.id) }
                        )
                    }
                    // Bottom padding for FAB
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }

        // FAB
        FloatingActionButton(
            onClick = { viewModel.openAddDialog() },
            containerColor = Accent,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Note")
        }
    }

    // ad or edit dialog
    if (showDialog) {
        NoteDialog(
            title = title,
            content = content,
            isEditing = editingNote != null,
            onTitleChange = viewModel::onTitleChange,
            onContentChange = viewModel::onContentChange,
            onSave = viewModel::saveNote,
            onDismiss = viewModel::dismissDialog
        )
    }
}

//card note
@Composable
fun NoteCard(
    note: Note,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { onEdit() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Accent dot
            Box(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Accent)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.content,
                    fontSize = 14.sp,
                    color = Muted,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.timestamp,
                    fontSize = 11.sp,
                    color = Color(0xFFBBBBBB)
                )
            }

            Column {
                IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Muted)
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFE05C5C))
                }
            }
        }
    }
}

// empty state
@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("📝", fontSize = 48.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text("No notes yet", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Primary)
        Text("Tap + to create your first note", fontSize = 14.sp, color = Muted)
    }
}

//add or edit the dialog
@Composable
fun NoteDialog(
    title: String,
    content: String,
    isEditing: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = Surface,
        title = {
            Text(
                text = if (isEditing) "Edit Note" else "New Note",
                fontWeight = FontWeight.Bold,
                color = Primary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Accent,
                        focusedLabelColor = Accent
                    )
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = onContentChange,
                    label = { Text("Content") },
                    minLines = 3,
                    maxLines = 6,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Accent,
                        focusedLabelColor = Accent
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                colors = ButtonDefaults.buttonColors(containerColor = Accent),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(if (isEditing) "Update" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Muted)
            }
        }
    )
}
