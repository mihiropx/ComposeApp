package com.example.composeapp.notes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composeapp.notes.*

@Composable
fun SetupNavGraph(
    navController: NavHostController
){
NavHost(navController = navController, startDestination = Navigation.Notes.route ){
    composable(route = Navigation.Notes.route){
        NotesScreen(navController)
    }
    composable(route = Navigation.AddNote.route){
        AddNoteScreen(navController)
    }
}
}