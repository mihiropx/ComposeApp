package com.example.composeapp.notes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.notes.NotesViewModel
import com.example.composeapp.notes.model.Notes
import com.example.composeapp.notes.navigation.Screen
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun AddNoteScreenPreview() {
    AddNoteScreen(
        navController = rememberNavController(),
        noteData = null
    )
}

@Composable
fun AddNoteScreen(navController: NavController, noteData: String?) {

    val viewModel: NotesViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    var isEditNote = false
    var notes: Notes? = null

    if (!noteData.isNullOrBlank()) {
        isEditNote = true
        notes = Gson().fromJson(noteData, Notes::class.java)
    }

    var noteTitle by rememberSaveable {
        mutableStateOf(
            if (isEditNote) notes?.title ?: "" else ""
        )
    }

    var noteDes by rememberSaveable {
        mutableStateOf(
            if (isEditNote) notes?.description ?: "" else ""
        )
    }

    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = noteTitle,
            onValueChange = { noteTitle = it },
            label = { Text(text = "Title") })
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            value = noteDes,
            onValueChange = { noteDes = it },
            label = { Text(text = "Description") })
        Button(modifier = Modifier.padding(top = 20.dp), onClick = {

            if (noteTitle.isNotBlank() or noteDes.isNotBlank()) {
                val note = Notes(
                    title = noteTitle,
                    description = noteDes,
                    id = if (isEditNote) notes?.id else null
                )

                coroutineScope.launch {
                    if (isEditNote)
                        viewModel.editNote(note)
                    else
                        viewModel.addNotes(note)
                }
            }

            navController.popBackStack()

        }) {
            Text(text = if (isEditNote) "Edit" else "Add")
        }
    }
}
