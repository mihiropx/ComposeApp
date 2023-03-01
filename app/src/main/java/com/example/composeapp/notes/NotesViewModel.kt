package com.example.composeapp.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.notes.data.repository.NoteRepository
import com.example.composeapp.notes.model.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    var notes = mutableStateListOf<Notes>()

    fun getNotes(): List<Notes> {

        repository.getNotes().onEach {
            notes.clear()
            notes.addAll(it)
        }.launchIn(viewModelScope)

        return notes
    }

    suspend fun getNotesById(id:Int):Notes{
        return repository.getNotesById(id)
    }

    suspend fun addNotes(note: Notes) {
        repository.addNote(note)
    }

    suspend fun deleteNote(note: Notes){
        repository.deleteNote(note)
    }

    suspend fun editNote(note: Notes) {
        repository.addNote(note)
    }
}