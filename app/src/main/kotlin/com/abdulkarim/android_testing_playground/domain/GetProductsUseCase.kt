package com.abdulkarim.android_testing_playground.domain

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<ProductApiEntity> {
        return repository.getProducts()
    }
}