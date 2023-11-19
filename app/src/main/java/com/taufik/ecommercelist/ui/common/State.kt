package com.taufik.ecommercelist.ui.common

sealed class State<out T> private constructor(){
    data class Success<out T>(val data: T): State<T>()
    data class Error(val error: String): State<Nothing>()
    object Loading: State<Nothing>()
}