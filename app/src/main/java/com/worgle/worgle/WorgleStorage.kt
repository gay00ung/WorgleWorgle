package com.worgle.worgle

import android.content.Context
import android.util.Log
import kotlinx.coroutines.runBlocking

object WorgleStorage {
    private const val PREFS_NAME = "worgle_prefs"
    private const val KEY_WORD = "today_word"
    private const val KEY_DATE = "today_date"

    fun getTodayWord(context: Context, apiKey: String): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedDate = prefs.getString(KEY_DATE, "")
        val todayDate = java.time.LocalDate.now().toString()
        val savedWord = prefs.getString(KEY_WORD, "")

        Log.d("WorgleStorage", "savedDate: $savedDate, todayDate: $todayDate")
        Log.d("WorgleStorage", "savedWord: ${savedWord ?: ""}")

        return if ((savedDate != todayDate) || savedWord.isNullOrEmpty()) {
            Log.d("WorgleStorage", "savedWord is null or savedDate is not today")
            val newWord = runBlocking { fetchRandomWordFromApi(apiKey) } ?: ""
            prefs.edit().apply {
                putString(KEY_WORD, newWord)
                putString(KEY_DATE, todayDate)
                apply()
            }
            newWord
        } else {
            Log.d("WorgleStorage", "++ savedWord is not null and savedDate is today")
            prefs.getString(KEY_WORD, "") ?: ""
        }
    }

}