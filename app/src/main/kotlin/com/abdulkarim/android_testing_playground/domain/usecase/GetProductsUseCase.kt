package com.abdulkarim.android_testing_playground.domain.usecase

import com.abdulkarim.android_testing_playground.common.Result
import com.abdulkarim.android_testing_playground.domain.repository.ProductRepository
import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Result<List<ProductApiEntity>>> {
        return repository.getProducts()
    }
}