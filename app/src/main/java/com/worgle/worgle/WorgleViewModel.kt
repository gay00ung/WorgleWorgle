package com.worgle.worgle

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worgle.worgle.data.RetrofitInstance
import com.worgle.worgle.network.MyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorgleViewModel(private val context: Context, private val apiKey: String): ViewModel() {
    private val _todayWord = MutableStateFlow<String?>(null)
    val todayWord: StateFlow<String?> get() = _todayWord

    fun fetchTodayWord() {
        viewModelScope.launch {
            _todayWord.value = WorgleStorage.getTodayWord(context, apiKey)
        }
    }
}