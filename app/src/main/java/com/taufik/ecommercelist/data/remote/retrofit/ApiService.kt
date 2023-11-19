package com.taufik.ecommercelist.data.remote.retrofit

import com.taufik.ecommercelist.data.remote.response.ProductResponse
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}