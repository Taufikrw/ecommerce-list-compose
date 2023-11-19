package com.taufik.ecommercelist.data.di

import android.content.Context
import com.taufik.ecommercelist.data.ProductRepository
import com.taufik.ecommercelist.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): ProductRepository {
        val apiService = ApiConfig.getApiService()
        return ProductRepository.getInstance(apiService)
    }
}