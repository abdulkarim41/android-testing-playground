package com.abdulkarim.android_testing_playground.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import com.abdulkarim.android_testing_playground.common.Result
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

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

}