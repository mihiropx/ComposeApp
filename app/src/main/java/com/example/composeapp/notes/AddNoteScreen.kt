package com.example.composeapp.notes

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun AddNoteScreenPreview() {
    AddNoteScreen(navController = rememberNavController())
}


@Composable
fun AddNoteScreen(navController: NavController) {
    var noteTitle by rememberSaveable { mutableStateOf("") }
    var noteDes by rememberSaveable { mutableStateOf("") }

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
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("key", "Note Added")

            navController.popBackStack()
        }) {
            Text(text = "Add")
        }
    }
}
