package com.worgle.worgle.network

import com.worgle.worgle.data.WordleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WorgleApi {
    @GET("api/search")
    suspend fun getRandomWord(
        @Query("key") apiKey: String,
        @Query("q") query: String, // 검색어
        @Query("req_type") reqType: String = "xml",
        @Query("num") num: Int = 1, // 단어 수
        @Query("advanced") advanced: Int = 1, // 고급 검색
        @Query("sort") sort: String = "popular" // 인기순
    ): WordleResponse
}