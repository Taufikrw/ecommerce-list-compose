package com.taufik.ecommercelist.ui.screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.ecommercelist.data.ProductRepository
import com.taufik.ecommercelist.data.local.Wishlist
import com.taufik.ecommercelist.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<State<List<Wishlist>>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<List<Wishlist>>>
        get() = _uiState

    fun getProducts() {
        _uiState.value = State.Loading
        viewModelScope.launch {
            repository.getProducts()
                .catch {
                    _uiState.value = State.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = State.Success(it)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: androidx.compose.runtime.State<String> get() = _query

    fun search(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.search(_query.value)
                .catch {
                    _uiState.value = State.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = State.Success(it)
                }
        }
    }
}