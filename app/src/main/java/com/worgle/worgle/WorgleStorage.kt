package com.worgle.worgle

import android.content.Context
import android.util.Log
import com.worgle.worgle.data.fetchRandomWordFromApi
import kotlinx.coroutines.runBlocking

object WorgleStorage {
    private const val PREFS_NAME = "worgle_prefs"
    private const val KEY_WORD = "today_word"
    private const val KEY_DATE = "today_date"

    fun getTodayWord(context: Context, apiKey: String): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedDate = prefs.getString(KEY_DATE, "")
        val todayDate = java.time.LocalDate.now().toString()

        Log.d("WorgleStorage", "savedDate: $savedDate, todayDate: $todayDate")
        Log.d("WorgleStorage", "savedWord: ${prefs.getString(KEY_WORD, "")}")

        return if (savedDate != todayDate) {
            val newWord = runBlocking { fetchRandomWordFromApi(apiKey) } ?: "단어 없음"
            prefs.edit().apply {
                putString(KEY_WORD, newWord)
                putString(KEY_DATE, todayDate)
                apply()
            }
            newWord
        } else {
            prefs.getString(KEY_WORD, "단어 없음") ?: "단어 없음"
        }
    }

}