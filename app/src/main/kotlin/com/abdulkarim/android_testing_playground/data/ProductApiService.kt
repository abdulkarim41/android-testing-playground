package com.abdulkarim.android_testing_playground.data

import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductApiResponse>
}