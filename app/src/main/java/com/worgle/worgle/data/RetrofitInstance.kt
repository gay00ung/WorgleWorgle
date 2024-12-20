package com.worgle.worgle.data

import android.util.Log
import com.worgle.worgle.network.WorgleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://krdict.korean.go.kr/api/search"

    val worgleApi: WorgleApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(WorgleApi::class.java)
    }
}

suspend fun fetchRandomWordFromApi(apiKey: String): String? {
    return withContext(Dispatchers.IO) {
        try {
            Log.d("WorgleStorage", "Fetching random word from API...")
            val response = RetrofitInstance.worgleApi.getRandomWord(apiKey, "")
            val wordList = response.items?.mapNotNull { it.word } ?: emptyList()
            if (wordList.isNotEmpty()) {
                Log.d("WorgleStorage", "Fetched random word: ${wordList.random()}")
                wordList.random() // 랜덤으로 하나 선택
            } else {
                null // 단어 없음
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("WorgleStorage", "Failed to fetch random word", e)
            null // 실패
        }
    }
}