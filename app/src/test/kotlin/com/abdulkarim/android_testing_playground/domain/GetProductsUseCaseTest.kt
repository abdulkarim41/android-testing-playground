package com.abdulkarim.android_testing_playground.domain

import com.abdulkarim.android_testing_playground.data.Product
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
        val products = listOf(Product(1, "Phone"), Product(2, "Tablet"))
        coEvery { repository.getProducts() } returns products

        val result = useCase()

        assertEquals(products, result)
        assertEquals(2, result.size)
    }
}