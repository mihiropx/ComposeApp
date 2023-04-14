package com.example.composeapp.notes.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.notes.NotesViewModel
import com.example.composeapp.notes.model.Notes
import com.example.composeapp.notes.navigation.Screen
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Preview
@Composable
fun NoteDetailScreenPreview() {
    NoteDetailsScreen(navController = rememberNavController(), "")
}

@Composable
fun NoteDetailsScreen(navController: NavHostController, noteData: String) {

    val viewModel: NotesViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    var showMenu by remember { mutableStateOf(false) }

    val note = Gson().fromJson(noteData, Notes::class.java)

    var noteTitle by remember {
        mutableStateOf(note?.title ?: "")
    }

    var noteDes by remember {
        mutableStateOf(note?.description ?: "")
    }

    var noteEmbeddings by remember {
        mutableStateOf(note?.embeddings ?: "")
    }

    LaunchedEffect(key1 = "", block = {
        note?.id?.let {

            val noteDemo = viewModel.getNotesById(it)
            Log.d("Note", "NoteDetailsScreen: $noteDemo")

            noteTitle = noteDemo.title.toString()
            noteDes = noteDemo.description.toString()
            noteDemo.embeddings?.let {  noteEmbeddings = it }
        }
    })

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                modifier = Modifier.weight(8f),
                text = noteTitle ?: "Untitled", fontSize = 24.sp, fontWeight = FontWeight.SemiBold
            )

            Box(modifier = Modifier.weight(1f)) {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Default.MoreVert, "More actions")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(
                        onClick = {
                            val noteData = Gson().toJson(note)
                            navController.navigate(Screen.AddNote.passNote(noteData))
                        },
                        text = { Text(text = "Edit") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit"
                             )
                        })
                    DropdownMenuItem(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.deleteNote(note)
                            }
                            navController.popBackStack()
                        },
                        text = { Text(text = "Delete") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        })
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(text = noteDes ?: "Not found", fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(text = noteEmbeddings.toString() ?: "Not found", fontSize = 16.sp)
    }
}
