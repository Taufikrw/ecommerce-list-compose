package com.taufik.ecommercelist.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.ecommercelist.data.ProductRepository
import com.taufik.ecommercelist.data.local.Profile
import com.taufik.ecommercelist.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProductRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<State<Profile>> =
        MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<Profile>>
        get() = _uiState

    fun getProfile() {
        viewModelScope.launch {
            _uiState.value = State.Loading
            _uiState.value = State.Success(repository.getProfile())
        }
    }
}