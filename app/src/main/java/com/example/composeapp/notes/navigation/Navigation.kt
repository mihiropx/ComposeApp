package com.example.composeapp.notes.navigation

sealed class Navigation(val route: String) {
    object Notes : Navigation("notes_screen")
    object AddNote : Navigation("add_note_screen")
}
