//package com.abdulkarim.android_testing_playground.data
//
//import com.abdulkarim.android_testing_playground.data.apiservice.ProductApiService
//import com.abdulkarim.android_testing_playground.data.model.ProductApiResponse
//import com.abdulkarim.android_testing_playground.data.repoimpl.ProductRepositoryImpl
//import io.mockk.coEvery
//import io.mockk.mockk
//import junit.framework.TestCase.assertEquals
//import kotlinx.coroutines.test.runTest
//import org.junit.Test
//
//class ProductRepositoryImplTest {
//
//    private val api: ProductApiService = mockk()
//    private val repository = ProductRepositoryImpl(api)
//
//    @Test
//    fun `should map product api response to domain model`() = runTest {
//        val dto = listOf(ProductApiResponse(1, "Phone"))
//        coEvery { api.getProducts() } returns dto
//
//        val result = repository.getProducts()
//
//        assertEquals("Phone", result.first().name)
//        assertEquals(1, result.first().id)
//    }
//
//}