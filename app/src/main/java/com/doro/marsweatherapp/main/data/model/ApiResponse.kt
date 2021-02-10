package com.doro.marsweatherapp.main.data.model

import com.google.gson.Gson
import retrofit2.Response

sealed class ApiResponse<T> {

    companion object {

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                val message = response.errorBody()?.string()
                val errorMessage = if (message.isNullOrEmpty()) {
                    response.message()
                } else {
                    //val error = Gson().fromJson(message, Error::class.java)
                    message
                }
                ApiErrorResponse(errorMessage ?: "unknown error")
            }
        }
    }

    data class Error(val error: String)
    class ApiEmptyResponse<T> : ApiResponse<T>()
    data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
    data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
}
