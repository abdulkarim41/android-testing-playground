package com.abdulkarim.android_testing_playground.domain

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetProductsUseCaseTest {

    private val repository: ProductRepository = mockk()
    private val useCase = GetProductsUseCase(repository)

    @Test
    fun `should return product list`() = runTest {
        val productApiEntities = listOf(ProductApiEntity(1, "Phone"), ProductApiEntity(2, "Tablet"))
        coEvery { repository.getProducts() } returns productApiEntities

        val result = useCase()

        assertEquals(productApiEntities, result)
        assertEquals(2, result.size)
    }
}