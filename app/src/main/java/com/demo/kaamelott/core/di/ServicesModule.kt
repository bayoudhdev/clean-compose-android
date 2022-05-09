package com.demo.kaamelott.core.di

import com.demo.kaamelott.data.services.QuotesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServicesModule {

    @Provides
    @Singleton
    fun provideQuotesService(retrofit: Retrofit): QuotesService =
        retrofit.create(QuotesService::class.java)
}
