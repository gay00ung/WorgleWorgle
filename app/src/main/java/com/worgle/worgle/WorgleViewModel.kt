package com.worgle.worgle

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worgle.worgle.data.RetrofitInstance
import com.worgle.worgle.network.MyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorgleViewModel(private val context: Context, private val apiKey: String): ViewModel() {
    val todayWord: String by lazy { WorgleStorage.getTodayWord(context, apiKey) }

//    var word: String = ""
//
//    fun getRandomWord() {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    RetrofitInstance.worgleApi.getRandomWord(apiKey, "")
//                }
//                val randomItem = response.items?.firstOrNull()
//                if (randomItem != null) {
//                    word = randomItem.word ?: "알 수 없는 단어입니다."
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                word = "단어를 가져오는데 실패했습니다."
//            }
//        }
//    }
}