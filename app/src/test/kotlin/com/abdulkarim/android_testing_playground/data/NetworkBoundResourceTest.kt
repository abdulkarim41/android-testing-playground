package com.abdulkarim.android_testing_playground.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import com.abdulkarim.android_testing_playground.common.Result
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkBoundResourceTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val networkBoundResource = NetworkBoundResource(dispatcher)

    @Test
    fun `fetchData emits Success when response successful`() = runTest {

        val results = networkBoundResource.fetchData {
            Response.success("Hello")
        }.toList()

        assertEquals(Result.Success("Hello"), results[2])
    }

    @Test
    fun `fetchData emits Error when body null`() = runTest {

        val results = networkBoundResource.fetchData<String> {
            Response.success(null)
        }.toList()

        val error = results[2] as Result.Error<*>
        assertEquals("Unknown error occurred", error.message)
        assertEquals(0, error.code)
    }

    @Test
    fun `fetchData emits API error when response unsuccessful`() = runTest {

        val errorBody = """{"message":"API error"}"""
            .toResponseBody(null)

        val results = networkBoundResource.fetchData<String> {
            Response.error(400, errorBody)
        }.toList()

        val error = results[2] as Result.Error<*>

        assertEquals("API error", error.message)
        assertEquals(400, error.code)
    }

    @Test
    fun `fetchData emits network error when IOException occurs`() = runTest {

        val results = networkBoundResource.fetchData<String> {
            throw IOException()
        }.toList()

        val error = results[2] as Result.Error<*>

        assertEquals("No internet connection, try again!", error.message)
    }

    @Test
    fun `fetchData emits timeout error when SocketTimeoutException occurs`() = runTest {

        val results = networkBoundResource.fetchData<String> {
            throw SocketTimeoutException()
        }.toList()

        val error = results[2] as Result.Error<*>

        assertEquals("Whoops! connection time out, try again!", error.message)
    }

    @Test
    fun `fetchData emits http error when HttpException occurs`() = runTest {

        val errorBody = """{"message":"HTTP error"}"""
            .toResponseBody(null)

        val response = Response.error<String>(500, errorBody)

        val exception = HttpException(response)

        val results = networkBoundResource.fetchData<String> {
            throw exception
        }.toList()

        val error = results[2] as Result.Error<*>

        assertEquals("HTTP error", error.message)
        assertEquals(500, error.code)
    }

    @Test
    fun `fetchData emits unknown error when unknown exception occurs`() = runTest {

        val results = networkBoundResource.fetchData<String> {
            throw RuntimeException()
        }.toList()

        val error = results[2] as Result.Error<*>

        assertEquals("Unknown error occur, please try again!", error.message)
    }

}