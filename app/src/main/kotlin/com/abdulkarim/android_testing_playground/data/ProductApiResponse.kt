package com.abdulkarim.android_testing_playground.data

import com.abdulkarim.android_testing_playground.domain.ProductApiEntity

data class ProductApiResponse(
    val id: Int,
    val name: String
)

fun ProductApiResponse.toDomain(): ProductApiEntity {
    return ProductApiEntity(id, name)
}


