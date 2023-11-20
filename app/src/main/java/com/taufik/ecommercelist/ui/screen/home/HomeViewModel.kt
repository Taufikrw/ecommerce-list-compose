package com.taufik.ecommercelist.ui.screen.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.ecommercelist.data.ProductRepository
import com.taufik.ecommercelist.data.remote.response.ProductsItem
import com.taufik.ecommercelist.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.http.Query

class HomeViewModel(
    private val repository: ProductRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<State<List<ProductsItem>>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<List<ProductsItem>>>
        get() = _uiState

    fun getProducts() {
        viewModelScope.launch {
            repository.getProducts()
                .catch {
                    _uiState.value = State.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = it
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
                    _uiState.value = it
                }
        }
    }
}