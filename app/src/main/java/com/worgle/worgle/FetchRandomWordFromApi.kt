package com.worgle.worgle

import android.util.Log
import com.worgle.worgle.data.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchRandomWordFromApi(apiKey: String): String? {
    return withContext(Dispatchers.IO) {
        while (true) {
            try {
                val randomChar = getRandomKoreanChar()
                Log.d("WorgleStorage", "Random Korean char: randomChar")
                val response = RetrofitInstance.worgleApi.getRandomWord(apiKey,randomChar.toString())
                val wordList = response.items?.mapNotNull { it.word } ?: emptyList()
                Log.d("WorgleStorage", "Fetched word list: $wordList")
                if (wordList.isNotEmpty()) {
                    val validWords = wordList.filter { it.length >= 2 }
                    if (validWords.isNotEmpty()) {
                        val selectedWord = validWords.random()
                        Log.d("WorgleStorage", "Fetched valid random word: $selectedWord")
                        return@withContext selectedWord
                    } else {
                        Log.d("WorgleStorage", "No valid words (2+ chars) found for $randomChar")
                    }
                } else {
                    Log.d("WorgleStorage", "No words found for $randomChar")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("WorgleStorage", "Failed to fetch random word", e)
            }
        }
        null
    }
}