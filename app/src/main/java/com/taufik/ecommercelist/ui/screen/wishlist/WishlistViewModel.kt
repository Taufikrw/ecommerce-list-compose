package com.taufik.ecommercelist.ui.screen.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.ecommercelist.data.ProductRepository
import com.taufik.ecommercelist.data.local.Wishlist
import com.taufik.ecommercelist.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val repository: ProductRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<State<List<Wishlist>>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<List<Wishlist>>>
        get() = _uiState

    fun getWishlistProduct() {
        viewModelScope.launch {
            repository.getWishlistProducts()
                .catch {
                    _uiState.value = State.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = State.Success(it)
                }
        }
    }
}