package com.taufik.ecommercelist.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.ecommercelist.data.ProductRepository
import com.taufik.ecommercelist.data.remote.response.ProductsItem
import com.taufik.ecommercelist.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<State<List<ProductsItem>>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<List<ProductsItem>>>
        get() = _uiState

    suspend fun getProducts() {
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
}