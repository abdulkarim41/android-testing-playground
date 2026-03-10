package com.abdulkarim.android_testing_playground.data

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
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

    @Test
    fun `should return product list`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("[{\"id\":1,\"name\":\"Phone\"}]"))

        val result = api.getProducts()

        assertEquals(1, result.size)
    }
}