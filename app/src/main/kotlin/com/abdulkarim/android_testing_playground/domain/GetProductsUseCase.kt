package com.abdulkarim.android_testing_playground.domain

import com.abdulkarim.android_testing_playground.data.Product

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}