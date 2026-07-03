package com.example.amphibianas.network

import com.example.amphibianas.model.Amphibian
import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getAmphibians() : List<Amphibian>

}