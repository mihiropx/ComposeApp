package com.example.composeapp.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "notes")
class Notes {

    var title: String? = null
    var description: String? = null
    var embeddings: List<Float>? = null
    @PrimaryKey
    var id: Int? = null
}

