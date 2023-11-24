package com.taufik.ecommercelist.data

import android.util.Log
import com.taufik.ecommercelist.data.local.Profile
import com.taufik.ecommercelist.data.local.ProfileData
import com.taufik.ecommercelist.data.local.Wishlist
import com.taufik.ecommercelist.data.remote.retrofit.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductRepository(
    private val apiService: ApiService
) {
    private val dataWishlist = mutableListOf<Wishlist>()

    init {
        GlobalScope.launch {
            if (dataWishlist.isEmpty()) {
                fetchData()
            }
        }
    }

    private suspend fun fetchData() {
        try {
            val response = apiService.getProducts().products
            response.forEach {
                dataWishlist.add(Wishlist(it, false))
            }
        } catch (e: Exception) {
            Log.e("Exception :", e.message.toString())
        }
    }

    fun getProducts(): Flow<List<Wishlist>> {
        return flowOf(dataWishlist)
    }

    fun getDetailProduct(id: Int): Wishlist {
        return dataWishlist.first {
            it.product.id == id
        }
    }

    fun search(query: String): Flow<List<Wishlist>> {
        val result = dataWishlist.filter {
            it.product.title.contains(query, ignoreCase = true)
        }
        return flowOf(result)
    }

    fun getProfile(): Profile {
        return ProfileData.data
    }

    fun updateWishlistProduct(id: Int, isWishlist: Boolean): Flow<Boolean> {
        val productId = dataWishlist.indexOfFirst { it.product.id == id }
        val result = if (productId >= 0) {
            val product = dataWishlist[productId]
            dataWishlist[productId] =
                product.copy(product = product.product, isWishlist = isWishlist)
            Log.d("TEST BANG: ", "MASUK REPO TRUE :${dataWishlist[productId]}")
            true
        } else {
            false
        }
        Log.d("TEST BANG: ", "MASUK REPO :$result")
        return flowOf(result)
    }

    fun getWishlistProducts(): Flow<List<Wishlist>> {
        return getProducts().map { data ->
            data.filter {
                it.isWishlist
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun clearInstance() {
            instance = null
        }

        fun getInstance(
            apiService: ApiService
        ): ProductRepository =
            instance ?: synchronized(this) {
                instance ?: ProductRepository(apiService)
            }.also { instance = it }
    }
}