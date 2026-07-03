package com.example.amphibianas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibianas.model.Amphibian
import com.example.amphibianas.network.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AmphibianUiState {
    data object Loading : AmphibianUiState
    data class Success(val posts: List<Amphibian>) : AmphibianUiState
    data class Error(val message: String) : AmphibianUiState
}

class AmphibianViewModel : ViewModel() {
    private val repository = Repository()
    private val _uiState =
        MutableStateFlow<AmphibianUiState>(AmphibianUiState.Loading)
    val uiState: StateFlow<AmphibianUiState> = _uiState

    init {
        loadAmphibians()
    }

    private fun loadAmphibians() {
        viewModelScope.launch {
            try {
                val posts = repository.getAmphibians()
                _uiState.value =
                    AmphibianUiState.Success(posts)
            } catch (e: Exception) {
                _uiState.value =
                    AmphibianUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}