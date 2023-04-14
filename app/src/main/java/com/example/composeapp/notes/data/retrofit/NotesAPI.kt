package com.example.composeapp.notes.data.retrofit

import com.example.composeapp.notes.model.EmbeddingRequest
import com.example.composeapp.notes.model.EmbeddingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface NotesAPI {

    @Headers("Content-Type: application/json")
    @POST("v1/embeddings")
    fun getEmbedding(@Body request: EmbeddingRequest?): Call<EmbeddingResponse?>?

}