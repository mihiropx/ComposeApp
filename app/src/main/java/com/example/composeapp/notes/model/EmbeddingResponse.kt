package com.example.composeapp.notes.model

import com.google.gson.annotations.SerializedName


data class EmbeddingResponse(

    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("model") var model: String? = null,
    @SerializedName("usage") var usage: Usage? = Usage()

)

data class Data(

    @SerializedName("embedding") var embedding: List<Float> = arrayListOf(),
    @SerializedName("index") var index: Int? = null,

    )

data class Usage(

    @SerializedName("prompt_tokens") var promptTokens: Int? = null,
    @SerializedName("total_tokens") var totalTokens: Int? = null

)