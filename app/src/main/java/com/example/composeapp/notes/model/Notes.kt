package com.example.composeapp.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "notes")
data class Notes(
    val title: String,
    val description: String,
    @PrimaryKey val id: Int?=null
)
