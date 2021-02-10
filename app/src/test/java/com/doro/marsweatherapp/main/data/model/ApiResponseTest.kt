package com.doro.marsweatherapp.main.data.model

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest  {

    @Test
    fun `test error message`() {
        val exception = Exception("Oops! Something went wrong")
        val (errorMessage) = ApiResponse.create<String>(exception)
        assertThat<String>(errorMessage, `is`("Oops! Something went wrong"))
    }

    @Test
    fun `test successful http request`() {
        val apiResponse: ApiResponse.ApiSuccessResponse<String> = ApiResponse
                .create<String>(Response.success("success")) as ApiResponse.ApiSuccessResponse<String>
        assertThat<String>(apiResponse.body, `is`("success"))
    }

    @Test
    fun `test error in http request`() {
        val errorResponse = Response.error<String>(
                400,
                "Oops! Something went wrong".toResponseBody("application/txt".toMediaTypeOrNull())
        )
        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiResponse.ApiErrorResponse<String>
        assertThat<String>(errorMessage, `is`("Oops! Something went wrong"))
    }
}