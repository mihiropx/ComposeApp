package com.example.composeapp.notes.data.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.composeapp.helper.Converters
import com.example.composeapp.notes.model.Notes

@Database(entities = [Notes::class], version = 1)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract val notesDao: NotesDao

    companion object{

        const val DATABASE_NAME = "notes_database"

    }
}