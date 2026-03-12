package com.abdulkarim.android_testing_playground.data.model

import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity

data class ProductApiResponse(
    val products: List<Product>
){
    data class Product(
        val id: Int,
        val title: String
    )
}

fun ProductApiResponse.Product.toProductApiEntity(): ProductApiEntity {
    return ProductApiEntity(
        id = id,
        name = title
    )
}

fun ProductApiResponse.toProductList(): List<ProductApiEntity> {
    return products.map { it.toProductApiEntity() }
}