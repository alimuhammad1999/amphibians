package com.example.amphibianas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibianas.model.Amphibian
import com.example.amphibianas.network.Repository
import com.example.amphibianas.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AmphibianUiState {
    data object Loading : AmphibianUiState
    data class Success(val amphibiansList: List<Amphibian>) : AmphibianUiState
    data class Error(val message: String) : AmphibianUiState
}

class AmphibianViewModel : ViewModel() {
    private val retrofit = RetrofitInstance.api
    private val _uiState =
        MutableStateFlow<AmphibianUiState>(AmphibianUiState.Loading)
    val uiState: StateFlow<AmphibianUiState> = _uiState

    init {
        loadAmphibians()
    }

    fun loadAmphibians() {
        viewModelScope.launch {
            try {
                val amphibians = retrofit.getAmphibians()
                _uiState.value =
                    AmphibianUiState.Success(amphibians)
            } catch (e: Exception) {
                _uiState.value =
                    AmphibianUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}