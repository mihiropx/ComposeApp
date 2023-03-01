package com.example.composeapp.notes.data.repository

import com.example.composeapp.notes.model.Notes
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes():Flow<List<Notes>>

    suspend fun getNotesById(id:Int):Notes

    suspend fun addNote(note:Notes)

    suspend fun deleteNote(note: Notes)

    suspend fun editNote(note: Notes)
}