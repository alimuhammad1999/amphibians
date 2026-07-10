package com.example.amphibianas

import android.app.Application
import com.example.amphibianas.network.AmphibianRepository
import com.example.amphibianas.network.AmphibiansApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

    val api: AmphibiansApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(AmphibiansApiService::class.java)
    }
    val repository = AmphibianRepository(api)
}

class AmphibianApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}