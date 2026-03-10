package com.abdulkarim.android_testing_playground.presentation

import com.abdulkarim.android_testing_playground.domain.GetProductsUseCase
import com.abdulkarim.android_testing_playground.domain.ProductApiEntity
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private val useCase: GetProductsUseCase = mockk()

    @Test
    fun `should emit products`() = runTest {
        val products = listOf(ProductApiEntity(1, "Phone"))
        coEvery { useCase.invoke() } returns products

        val viewModel = ProductViewModel(
            getProductsUseCase = useCase,
            dispatcher = StandardTestDispatcher(testScheduler)
        )
        viewModel.loadProducts()

        advanceUntilIdle()

        assertEquals(products, viewModel.products.value)
    }
}