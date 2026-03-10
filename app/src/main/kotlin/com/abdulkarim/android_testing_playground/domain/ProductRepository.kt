package com.abdulkarim.android_testing_playground.domain

interface ProductRepository {
    suspend fun getProducts(): List<ProductApiEntity>
}