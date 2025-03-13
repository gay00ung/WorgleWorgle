package com.worgle.worgle.network

import com.worgle.worgle.data.WordleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WorgleApi {
    @GET("api/search")
    suspend fun getRandomWord(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("num") num: Int = 100,
        @Query("part") part: String = "word",
        @Query("translated") translated: String = "n",
        @Query("advanced") advanced: String = "n",
        @Query("method") method: String = "include"
    ): WordleResponse
}