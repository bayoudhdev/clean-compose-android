package com.demo.kaamelott.core.di

import com.demo.kaamelott.data.datasources.remote.QuotesRemoteDataSource
import com.demo.kaamelott.data.repositories.QuotesRepositoryImpl
import com.demo.kaamelott.data.services.QuotesService
import com.demo.kaamelott.domain.repositories.QuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class QuotesModule {

    @Provides
    @Singleton
    fun provideQuotesService(retrofit: Retrofit): QuotesService =
        retrofit.create(QuotesService::class.java)

    @Provides
    @Singleton
    fun provideQuotesRepository(quotesRemoteDataSource: QuotesRemoteDataSource): QuotesRepository =
        QuotesRepositoryImpl(quotesRemoteDataSource = quotesRemoteDataSource)
}
