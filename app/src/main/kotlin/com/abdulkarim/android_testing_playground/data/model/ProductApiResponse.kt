package com.abdulkarim.android_testing_playground.data.model

import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity

data class ProductApiResponse(
    val id: Int,
    val name: String
)

fun ProductApiResponse.toDomain(): ProductApiEntity {
    return ProductApiEntity(id, name)
}


