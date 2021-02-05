package com.doro.marsweatherapp.main.di

import com.doro.marsweatherapp.BuildConfig
import com.doro.marsweatherapp.main.data.api.WeatherService
import com.doro.marsweatherapp.main.data.interceptor.ApiInterceptor
import com.doro.marsweatherapp.main.data.mapper.WeatherInformationMapper
import com.doro.marsweatherapp.main.data.repository.WeatherRepositoryImpl
import com.doro.marsweatherapp.main.domain.interactor.GetWeatherUseCase
import com.doro.marsweatherapp.main.domain.repository.WeatherRepository
import com.doro.marsweatherapp.main.presentation.MainAdapter
import com.doro.marsweatherapp.main.presentation.MainViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val mainModule = module {

    viewModel { MainViewModel(get()) }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            })
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
            .build()
    }

    factory {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    factory { WeatherInformationMapper() }
    single { MainAdapter() }
    single { Gson() }
    factory { get<Retrofit>().create(WeatherService::class.java) }
    single { GetWeatherUseCase(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}