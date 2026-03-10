package com.abdulkarim.android_testing_playground.data

import com.abdulkarim.android_testing_playground.domain.ProductApiEntity
import com.abdulkarim.android_testing_playground.domain.ProductRepository

class ProductRepositoryImpl(private val api: ProductApiService): ProductRepository {
    override suspend fun getProducts(): List<ProductApiEntity> {
        return api.getProducts().map { it.toDomain() }
    }

}