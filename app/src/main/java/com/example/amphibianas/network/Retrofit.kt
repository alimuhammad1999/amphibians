package com.example.amphibianas.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{
    private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

    val api: AmphibiansApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(AmphibiansApiService::class.java)
    }
}