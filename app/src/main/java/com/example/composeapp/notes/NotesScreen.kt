@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composeapp.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.notes.navigation.Navigation

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NotesScreen(navController = rememberNavController())
}

@Composable
fun NotesScreen(navController: NavHostController) {

    val notes = remember { mutableStateListOf<String>() }

    if (navController.currentBackStackEntry!!.savedStateHandle.contains("key")) {
        val keyData =
            navController.currentBackStackEntry!!.savedStateHandle.get<String>(
                "key"
            ) ?: ""
        navController.currentBackStackEntry!!.savedStateHandle.remove<String>("key")
        notes.add(keyData)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

                navController.navigate(Navigation.AddNote.route){

                    restoreState=true
                }

            }) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        LazyColumn {
            items(notes) { item ->
                NoteItemView(item)
            }
        }
    }

}

@Composable
fun NoteItemView(item: String) {

    Column {
        Text(text = item, modifier = Modifier.padding(20.dp))
        Divider()
    }
}
