package com.example.composeapp.notes.model

import com.google.gson.annotations.SerializedName


class EmbeddingRequest(

    @SerializedName("input")
    private val input: String,

    @SerializedName("model")
    private val model: String = "text-embedding-ada-002"


)