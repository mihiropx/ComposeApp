package com.example.composeapp.notes.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NotesScreen(navController = rememberNavController())
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(navController: NavHostController) {

    val viewModel: NotesViewModel = hiltViewModel()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

                navController.navigate(Screen.AddNote.route) {
                    launchSingleTop = true
                    restoreState = true
                }

            }) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        LazyColumn {
            items(viewModel.getNotes()) { note ->
                NoteItemView(note,navController)
            }
        }
    }

}

@Composable
fun NoteItemView(note: Notes,navController: NavHostController) {

    val viewModel: NotesViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable {
                    val noteJson = Gson().toJson(note)
                    navController.navigate(Screen.NoteDetails.withStringArgs(noteJson))
                }
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
            ) {
                Text(
                    text = note.title,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = note.description,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            IconButton(content = {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Note"
                )
            },
                onClick = {
                    coroutineScope.launch {
                        viewModel.deleteNote(note)
                    }
                }
            )
        }

        Divider()
    }

}
