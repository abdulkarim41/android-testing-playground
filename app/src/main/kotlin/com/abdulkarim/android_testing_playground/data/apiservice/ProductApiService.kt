package com.abdulkarim.android_testing_playground.data.apiservice

import com.abdulkarim.android_testing_playground.data.model.ProductApiResponse
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductApiResponse>
}