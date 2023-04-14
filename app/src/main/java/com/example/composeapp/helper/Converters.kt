package com.example.composeapp.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromFloatList(floatList: List<Float>): String {
        return Gson().toJson(floatList)
    }

    @TypeConverter
    fun toFloatList(floatListString: String): List<Float> {
        val listType = object : TypeToken<List<Float>>() {}.type
        return Gson().fromJson(floatListString, listType)
    }
}
