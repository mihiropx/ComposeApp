package com.example.composeapp.notes.navigation

sealed class Screen(val route: String) {
    object Notes : Screen("notes_screen")

    object AddNote : Screen("add_note_screen"){

        fun passNote(note: String): String {
            return buildString {
                append(route)
                append("?note=$note")
            }
        }
    }

    object NoteDetails : Screen("note_details_screen")

    fun withStringArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }

    fun withIntArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }

}
