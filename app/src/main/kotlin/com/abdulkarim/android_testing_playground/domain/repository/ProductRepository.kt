package com.abdulkarim.android_testing_playground.domain.repository

import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity

interface ProductRepository {
    suspend fun getProducts(): List<ProductApiEntity>
}