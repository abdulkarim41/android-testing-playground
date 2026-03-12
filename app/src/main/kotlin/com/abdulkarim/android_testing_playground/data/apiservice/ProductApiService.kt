package com.abdulkarim.android_testing_playground.data.apiservice

import com.abdulkarim.android_testing_playground.data.model.ProductApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): Response<ProductApiResponse>
}