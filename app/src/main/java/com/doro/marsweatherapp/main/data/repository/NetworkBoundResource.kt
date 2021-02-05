package com.doro.marsweatherapp.main.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.doro.marsweatherapp.main.data.model.ApiResponse
import com.doro.marsweatherapp.main.data.repository.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow() = flow {
        emit(Resource.loading())

        when (val apiResponse = createCall()) {
            is ApiResponse.ApiSuccessResponse -> {
                emitAll(loadResults(processResponse(apiResponse)).map { Resource.success(it) })
            }

            is ApiResponse.ApiEmptyResponse -> {
                emitAll(loadResults().map { Resource.success(it) })
            }

            is ApiResponse.ApiErrorResponse -> {
                emit(Resource.error(apiResponse.errorMessage))
            }
        }
    }

    @WorkerThread
    protected open fun processResponse(response: ApiResponse.ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract suspend fun loadResults(item: RequestType? = null) : Flow<ResultType>

    @MainThread
    protected abstract suspend fun createCall(): ApiResponse<RequestType>
}