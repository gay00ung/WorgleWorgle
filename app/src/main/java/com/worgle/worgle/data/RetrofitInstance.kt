package com.worgle.worgle.data

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
            val response = RetrofitInstance.worgleApi.getRandomWord(apiKey, "")
            val wordList = response.items?.mapNotNull { it.word } ?: emptyList()
            if (wordList.isNotEmpty()) {
                wordList.random() // 랜덤으로 하나 선택
            } else {
                null // 단어 없음
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null // 실패
        }
    }
}