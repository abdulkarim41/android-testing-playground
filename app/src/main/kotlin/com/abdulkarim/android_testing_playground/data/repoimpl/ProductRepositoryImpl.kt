package com.abdulkarim.android_testing_playground.data.repoimpl

import com.abdulkarim.android_testing_playground.data.apiservice.ProductApiService
import com.abdulkarim.android_testing_playground.data.model.toDomain
import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity
import com.abdulkarim.android_testing_playground.domain.repository.ProductRepository

class ProductRepositoryImpl(private val api: ProductApiService): ProductRepository {
    override suspend fun getProducts(): List<ProductApiEntity> {
        return api.getProducts().map { it.toDomain() }
    }

}