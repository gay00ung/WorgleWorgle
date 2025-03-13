package com.worgle.worgle.data


import com.worgle.worgle.network.WorgleApi
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://krdict.korean.go.kr/"

    val worgleApi: WorgleApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(WorgleApi::class.java)
    }
}