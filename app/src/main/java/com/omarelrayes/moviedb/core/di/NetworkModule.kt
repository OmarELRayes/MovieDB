package com.omarelrayes.moviedb.core.di

import androidx.viewbinding.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.omarelrayes.moviedb.core.api.ApiConstants
import com.omarelrayes.moviedb.core.api.RequestInterceptor
import com.omarelrayes.moviedb.features.list.data.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CONTENT_TYPE_VALUE = "application/json"

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(getJsonConverterFactory())
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val json: Json
        get() = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getJsonConverterFactory(): Converter.Factory {
        return json.asConverterFactory(CONTENT_TYPE_VALUE.toMediaType())
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }
}
