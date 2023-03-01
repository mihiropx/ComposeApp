package com.example.composeapp.notes.ui

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

    val note=Gson().fromJson(noteData,Notes::class.java)

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
                text = note?.title?:"Untitled", fontSize = 24.sp, fontWeight = FontWeight.SemiBold
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
        Text(text = note?.description?:"Not found", fontSize = 16.sp)
    }
}
