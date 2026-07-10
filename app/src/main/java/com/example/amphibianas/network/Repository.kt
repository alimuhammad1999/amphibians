package com.example.amphibianas.network

import com.example.amphibianas.model.Amphibian

class AmphibianRepository (
    private val api : AmphibiansApiService
) {
    suspend fun getAmphibians() : List<Amphibian> {
        return api.getAmphibians()
    }
}