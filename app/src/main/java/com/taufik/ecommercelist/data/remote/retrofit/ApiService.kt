package com.taufik.ecommercelist.data.remote.retrofit

import com.taufik.ecommercelist.data.remote.response.ProductResponse
import com.taufik.ecommercelist.data.remote.response.ProductsItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("products/{id}")
    suspend fun getDetailProduct(
        @Path("id")
        id: Int
    ): ProductsItem
}