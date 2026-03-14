package com.abdulkarim.android_testing_playground.presentation

import app.cash.turbine.test
import com.abdulkarim.android_testing_playground.domain.usecase.GetProductsUseCase
import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity
import com.abdulkarim.android_testing_playground.common.Result
import com.abdulkarim.android_testing_playground.presentation.product.ProductListUiAction
import com.abdulkarim.android_testing_playground.presentation.product.ProductListUiState
import com.abdulkarim.android_testing_playground.presentation.product.ProductViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private val useCase: GetProductsUseCase = mockk()

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `init fetch emits loading then success`() = runTest {
        val fakeProducts = listOf(
            ProductApiEntity(1, "Phone"),
            ProductApiEntity(2, "Laptop")
        )

        coEvery { useCase.invoke() } returns flow {
            emit(Result.Loading(true))
            emit(Result.Success(fakeProducts))
            emit(Result.Loading(false))
        }

        val viewModel = ProductViewModel(useCase)

        val job = launch {
            viewModel.uiState.test {
                val loadingStart = awaitItem()
                assertTrue(loadingStart is ProductListUiState.Loading)
                assertTrue((loadingStart as ProductListUiState.Loading).isLoading)

                val success = awaitItem()
                assertTrue(success is ProductListUiState.ProductList)
                assertEquals(fakeProducts, (success as ProductListUiState.ProductList).data)

                val loadingEnd = awaitItem()
                assertTrue(loadingEnd is ProductListUiState.Loading)
                assertFalse((loadingEnd as ProductListUiState.Loading).isLoading)

                cancel()
            }
        }
        job.join()
    }

//    @Test
//    fun `fetchProductListApi emits error`() = runTest {
//        val errorMessage = "Network failure"
//        coEvery { useCase.invoke() } returns flow {
//            emit(Result.Error(errorMessage, 0))
//        }
//
//        val viewModel = ProductViewModel(useCase)
//
//        val job = launch {
//            viewModel.uiState.test {
//                skipItems(1)
//                val errorState = awaitItem()
//                assertTrue(errorState is ProductListUiState.ApiError)
//                assertEquals(errorMessage, (errorState as ProductListUiState.ApiError).message)
//                cancel()
//            }
//        }
//        job.join()
//    }

    @Test
    fun `fetchProductListApi emits only loading when loading true`() = runTest {

        coEvery { useCase.invoke() } returns flow {
            emit(Result.Loading(true))
        }
        val viewModel = ProductViewModel(useCase)

        val job = launch {
            viewModel.uiState.test {
                val loadingState = awaitItem()
                assertTrue(loadingState is ProductListUiState.Loading)
                assertTrue((loadingState as ProductListUiState.Loading).isLoading)
                cancel()
            }
        }
        job.join()
    }

//    @Test
//    fun `fetchProductListApi emits empty list`() = runTest {
//        coEvery { useCase.invoke() } returns flow {
//            emit(Result.Success(emptyList()))
//        }
//
//        val viewModel = ProductViewModel(useCase)
//
//        viewModel.action(ProductListUiAction.FetchProductListApi)
//
//        val job = launch {
//            viewModel.uiState.test {
//                skipItems(1)
//                val success = awaitItem()
//                assertTrue(success is ProductListUiState.ProductList)
//                assertTrue((success as ProductListUiState.ProductList).data.isEmpty())
//                cancel()
//            }
//        }
//        job.join()
//    }

}