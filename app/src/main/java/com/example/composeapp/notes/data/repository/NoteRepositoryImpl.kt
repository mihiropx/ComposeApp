package com.example.composeapp.notes.data.repository

import com.example.composeapp.notes.data.local_db.NotesDao
import com.example.composeapp.notes.model.Notes
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val notesDao: NotesDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Notes>> {
        return notesDao.getNotes()
    }

    override suspend fun getNotesById(id: Int): Notes {
        return notesDao.getNoteById(id)
    }

    override suspend fun addNote(note: Notes) {
        notesDao.addNote(note)
    }

    override suspend fun deleteNote(note: Notes) {
        notesDao.deleteNote(note)
    }

    override suspend fun editNote(note: Notes) {
        notesDao.editNote(note)
    }
}