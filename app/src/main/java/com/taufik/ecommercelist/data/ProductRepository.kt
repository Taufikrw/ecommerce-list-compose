package com.taufik.ecommercelist.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.taufik.ecommercelist.data.model.Profile
import com.taufik.ecommercelist.data.model.ProfileData
import com.taufik.ecommercelist.data.remote.response.ProductsItem
import com.taufik.ecommercelist.data.remote.retrofit.ApiService
import com.taufik.ecommercelist.ui.common.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class ProductRepository(
    private val apiService: ApiService
) {
    suspend fun getProducts(): Flow<List<ProductsItem>> {
        return try {
            val response = apiService.getProducts().products
            flowOf(response)
        } catch (e: Exception) {
            Log.e("Exception: ", e.message.toString())
            flowOf()
        }
    }

    suspend fun getDetailProduct(id: Int): ProductsItem? {
        return try {
            val response = apiService.getDetailProduct(id)
            response
        } catch (e: Exception) {
            Log.e("Exception: ", e.message.toString())
            null
        }
    }

    fun getProfile(): Profile {
        return ProfileData.data
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