package com.example.composeapp.notes.data.local_db

import androidx.room.*
import com.example.composeapp.notes.model.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Notes>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Notes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Notes)

    @Delete
    suspend fun deleteNote(note:Notes)

    @Update
    fun editNote(note:Notes)
}