package com.example.composeapp.notes.di

import android.app.Application
import androidx.room.Room
import com.example.composeapp.notes.data.local_db.NoteDatabase
import com.example.composeapp.notes.data.local_db.NoteDatabase.Companion.DATABASE_NAME
import com.example.composeapp.notes.data.repository.NoteRepository
import com.example.composeapp.notes.data.repository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.notesDao)
    }
}