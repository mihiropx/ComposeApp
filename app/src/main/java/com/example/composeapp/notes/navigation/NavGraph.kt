package com.example.composeapp.notes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeapp.notes.ui.AddNoteScreen
import com.example.composeapp.notes.ui.NoteDetailsScreen
import com.example.composeapp.notes.ui.NotesScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Notes.route) {
        composable(route = Screen.Notes.route) {
            NotesScreen(navController)
        }
        composable(route = Screen.AddNote.route + "?note={note}",
            arguments = listOf(
                navArgument("note") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            AddNoteScreen(navController, noteData = it.arguments?.getString("note"))
        }
        composable(route = Screen.NoteDetails.route + "/{note}",
            arguments = listOf(
                navArgument("note") {
                    type = NavType.StringType
                }
            )
        ) {
            NoteDetailsScreen(navController, noteData = it.arguments?.getString("note") ?: "")
        }
    }
}