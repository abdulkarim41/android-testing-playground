package com.abdulkarim.android_testing_playground.domain

import app.cash.turbine.test
import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity
import com.abdulkarim.android_testing_playground.domain.repository.ProductRepository
import com.abdulkarim.android_testing_playground.domain.usecase.GetProductsUseCase
import com.abdulkarim.android_testing_playground.common.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetProductsUseCaseTest {

    private val repository: ProductRepository = mockk()
    private val useCase = GetProductsUseCase(repository)

    @Test
    fun `should return product list successfully`() = runTest {

        val products = listOf(ProductApiEntity(1, "Phone"), ProductApiEntity(2, "Tablet"))

        coEvery { repository.getProducts() } returns flow {
            emit(Result.Success(products))
        }

        useCase().test {
            val result = awaitItem()
            assert(result is Result.Success)
            assertEquals(2, (result as Result.Success).data.size)
            assertEquals("Phone", result.data[0].name)
            assertEquals("Tablet", result.data[1].name)

            awaitComplete()
        }
    }

    @Test
    fun `should return empty list`() = runTest {
        coEvery { repository.getProducts() } returns flow {
            emit(Result.Success(emptyList()))
        }

        useCase().test {
            val result = awaitItem()
            assert(result is Result.Success)
            assert((result as Result.Success).data.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `should emit loading state`() = runTest {
        coEvery { repository.getProducts() } returns flow {
            emit(Result.Loading(true))
            emit(Result.Loading(false))
        }

        useCase().test {
            assert(awaitItem() is Result.Loading)
            assert(awaitItem() is Result.Loading)
            awaitComplete()
        }
    }

    @Test
    fun `should emit loading then success`() = runTest {
        val productApiEntities = listOf(ProductApiEntity(1, "Phone"))

        coEvery { repository.getProducts() } returns flow {
            emit(Result.Loading(true))
            emit(Result.Success(productApiEntities))
            emit(Result.Loading(false))
        }

        useCase().test {
            val first = awaitItem()
            assert(first is Result.Loading && (first).loading)

            val success = awaitItem()
            assert(success is Result.Success)
            assertEquals(1, (success as Result.Success).data.size)

            val last = awaitItem()
            assert(last is Result.Loading && !(last).loading)

            awaitComplete()
        }
    }

    @Test
    fun `should emit error state`() = runTest {
        val errorMessage = "Network error"
        val errorCode = 500

        coEvery { repository.getProducts() } returns flow {
            emit(Result.Error(errorMessage, errorCode))
        }

        useCase().test {
            val result = awaitItem()
            assert(result is Result.Error)
            assertEquals(errorMessage, (result as Result.Error).message)
            assertEquals(errorCode, result.code)
            awaitComplete()
        }
    }

}