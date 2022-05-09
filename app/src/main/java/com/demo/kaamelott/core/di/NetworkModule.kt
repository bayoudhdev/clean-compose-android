package com.demo.kaamelott.core.di

import android.content.Context
import com.demo.kaamelott.core.bases.BASE_URL
import com.demo.kaamelott.core.network.interceptors.NetworkStatusInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        networkStatusInterceptor: NetworkStatusInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient
        .Builder()
        .cache(Cache(File(context.cacheDir, "api_cache"), 50L * 1024 * 1024))
        .addInterceptor(networkStatusInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    @ExperimentalSerializationApi
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }
}
