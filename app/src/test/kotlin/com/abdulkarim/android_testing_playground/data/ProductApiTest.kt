package com.abdulkarim.android_testing_playground.data

import com.abdulkarim.android_testing_playground.data.apiservice.ProductApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class ProductApiTest {

    private lateinit var api: ProductApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getProducts returns product list on success`() = runTest {
        val mockResponse = """
        {
            "products": [
                {"id": 1, "title": "Phone"},
                {"id": 2, "title": "Laptop"}
            ]
        }
    """.trimIndent()

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponse))

        val response = api.getProducts()
        val products = response.body()?.products

        assertEquals(2, products?.size)
        assertEquals("Phone", products?.get(0)?.title)
        assertEquals(1, products?.get(0)?.id)
    }

    @Test
    fun `getProducts returns empty list`() = runTest {
        val mockResponse = """
        {"products": []}
    """.trimIndent()

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponse))

        val response = api.getProducts()
        val products = response.body()?.products

        assertTrue(products?.isEmpty() == true)
    }

    @Test
    fun `getProducts returns error response`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        val response = api.getProducts()

        assertFalse(response.isSuccessful)
        assertEquals(404, response.code())
    }

}