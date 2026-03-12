package com.abdulkarim.android_testing_playground.domain.repository

import com.abdulkarim.android_testing_playground.common.Result
import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Result<List<ProductApiEntity>>>
}