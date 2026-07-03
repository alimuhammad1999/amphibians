package com.example.amphibianas.network

import com.example.amphibianas.model.Amphibian

class Repository {
    suspend fun getAmphibians() : List<Amphibian> {
        return RetrofitInstance.api.getAmphibians()
    }
}