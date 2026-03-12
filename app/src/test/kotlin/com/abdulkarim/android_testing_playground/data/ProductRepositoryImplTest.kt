package com.abdulkarim.android_testing_playground.data

import app.cash.turbine.test
import com.abdulkarim.android_testing_playground.data.apiservice.ProductApiService
import com.abdulkarim.android_testing_playground.data.model.ProductApiResponse
import com.abdulkarim.android_testing_playground.data.repoimpl.ProductRepositoryImpl
import com.abdulkarim.android_testing_playground.common.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import retrofit2.Response
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryImplTest {

    private val api: ProductApiService = mockk()
    private lateinit var networkBoundResource: NetworkBoundResource
    private lateinit var repository: ProductRepositoryImpl
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        networkBoundResource = NetworkBoundResource(testDispatcher)
        repository = ProductRepositoryImpl(networkBoundResource, api)
    }

    @Test
    fun `getProducts emits success`() = runTest {

        val fakeResponse = ProductApiResponse(
            products = listOf(ProductApiResponse.Product(1, "Phone"))
        )
        coEvery { api.getProducts() } returns Response.success(fakeResponse)

        repository.getProducts().test {
            val loadingStart = awaitItem()
            assertTrue(loadingStart is Result.Loading && (loadingStart).loading)

            val loadingEnd = awaitItem()
            assertTrue(loadingEnd is Result.Loading && !(loadingEnd).loading)

            val success = awaitItem()
            assertTrue(success is Result.Success)

            val products = (success as Result.Success).data
            assertEquals(1, products.size)
            assertEquals("Phone", products[0].name)

            awaitComplete()
        }
    }

    @Test
    fun `getProducts emits success with multiple products`() = runTest {
        val fakeResponse = ProductApiResponse(
            products = listOf(
                ProductApiResponse.Product(1, "Phone"),
                ProductApiResponse.Product(2, "Laptop")
            )
        )
        coEvery { api.getProducts() } returns Response.success(fakeResponse)

        repository.getProducts().test {
            awaitItem()
            awaitItem()

            val success = awaitItem() as Result.Success
            val products = success.data
            assertEquals(2, products.size)
            assertEquals("Phone", products[0].name)
            assertEquals("Laptop", products[1].name)
            awaitComplete()
        }
    }


    @Test
    fun `getProducts emits error on exception`() = runTest {

        coEvery { api.getProducts() } throws RuntimeException("Unknown error occurred")

        repository.getProducts().test {
            val loadingStart = awaitItem()
            assertTrue(loadingStart is Result.Loading && loadingStart.loading)

            val loadingEnd = awaitItem()
            assertTrue(loadingEnd is Result.Loading && !loadingEnd.loading)

            val error = awaitItem()
            assertTrue(error is Result.Error)
            assertEquals("Unknown error occurred", (error as Result.Error).message)
            awaitComplete()
        }
    }

}