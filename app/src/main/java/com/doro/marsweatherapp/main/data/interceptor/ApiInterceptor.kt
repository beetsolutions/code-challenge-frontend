package com.doro.marsweatherapp.main.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter(PARAM_API_ID, "DEMO_KEY")
            .addQueryParameter(PARAM_TYPE, "json")
            .addQueryParameter(PARAM_VERSION, "1.0")
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val PARAM_API_ID: String = "api_key"
        private const val PARAM_TYPE = "feedtype"
        private const val PARAM_VERSION = "1.0"
    }
}
