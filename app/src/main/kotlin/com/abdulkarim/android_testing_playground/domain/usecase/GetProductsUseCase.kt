package com.abdulkarim.android_testing_playground.domain.usecase

import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity
import com.abdulkarim.android_testing_playground.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<ProductApiEntity> {
        return repository.getProducts()
    }
}