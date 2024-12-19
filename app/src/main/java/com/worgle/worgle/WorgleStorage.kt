package com.worgle.worgle

import android.content.Context
import com.worgle.worgle.data.fetchRandomWordFromApi
import com.worgle.worgle.network.WorgleApi
import kotlinx.coroutines.runBlocking

object WorgleStorage {
    private const val PREFS_NAME = "worgle_prefs"
    private const val KEY_WORD = "today_word"
    private const val KEY_DATE = "today_date"

    fun getTodayWord(context: Context, apiKey: String): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedDate = prefs.getString(KEY_DATE, "")
        val todayDate = java.time.LocalDate.now().toString()

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