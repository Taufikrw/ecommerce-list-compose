package com.taufik.ecommercelist.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.ecommercelist.data.ProductRepository
import com.taufik.ecommercelist.data.remote.response.ProductsItem
import com.taufik.ecommercelist.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ProductRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<State<ProductsItem>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<ProductsItem>>
        get() = _uiState

    fun getDetailProduct(id: Int) {
        viewModelScope.launch {
            _uiState.value = State.Loading
            _uiState.value = State.Success(repository.getDetailProduct(id)!!)
        }
    }
}