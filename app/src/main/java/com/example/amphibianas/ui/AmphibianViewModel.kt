package com.example.amphibianas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibianas.AmphibianApplication
import com.example.amphibianas.model.Amphibian
import com.example.amphibianas.network.AmphibianRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AmphibianUiState {
    data object Loading : AmphibianUiState
    data class Success(val amphibiansList: List<Amphibian>) : AmphibianUiState
    data class Error(val message: String) : AmphibianUiState
}

class AmphibianViewModel (
    private val repository : AmphibianRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<AmphibianUiState>(AmphibianUiState.Loading)
    val uiState: StateFlow<AmphibianUiState> = _uiState

    init {
        loadAmphibians()
    }

    fun loadAmphibians() {
        viewModelScope.launch {
            try {
                delay(1500)
                val amphibians = repository.getAmphibians()
                _uiState.value =
                    AmphibianUiState.Success(amphibians)
            } catch (e: Exception) {
                _uiState.value =
                    AmphibianUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}