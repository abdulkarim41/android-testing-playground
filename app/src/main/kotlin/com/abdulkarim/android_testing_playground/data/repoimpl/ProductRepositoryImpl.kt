package com.abdulkarim.android_testing_playground.data.repoimpl

import com.abdulkarim.android_testing_playground.common.Result
import com.abdulkarim.android_testing_playground.data.NetworkBoundResource
import com.abdulkarim.android_testing_playground.data.apiservice.ProductApiService
import com.abdulkarim.android_testing_playground.data.model.toProductList
import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity
import com.abdulkarim.android_testing_playground.domain.repository.ProductRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl @Inject constructor(
    private val networkBoundResource: NetworkBoundResource,
    private val api: ProductApiService,
): ProductRepository {
    override suspend fun getProducts(): Flow<Result<List<ProductApiEntity>>> {
        return networkBoundResource
            .fetchData { api.getProducts() }
            .map { result ->
                when (result) {
                    is Result.Success -> {
                        val products = result.data.toProductList()
                        Result.Success(products)
                    }
                    is Result.Error -> Result.Error(result.message, result.code)
                    is Result.Loading -> Result.Loading(result.loading)
                }
            }
    }
}