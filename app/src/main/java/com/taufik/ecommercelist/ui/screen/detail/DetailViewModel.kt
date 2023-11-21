package com.taufik.ecommercelist.ui.screen.detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.ecommercelist.data.ProductRepository
import com.taufik.ecommercelist.data.local.Wishlist
import com.taufik.ecommercelist.data.remote.response.ProductsItem
import com.taufik.ecommercelist.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ProductRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<State<Wishlist>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<Wishlist>>
        get() = _uiState

    fun getDetailProduct(id: Int) {
        viewModelScope.launch {
            _uiState.value = State.Loading
            _uiState.value = State.Success(repository.getDetailProduct(id))
        }
    }

    fun updateWishlist(product: ProductsItem, isWishlist: Boolean) {
        viewModelScope.launch {
            repository.updateWishlistProduct(product.id, isWishlist)
        }
    }
}