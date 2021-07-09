package com.jt.stockquoteshiltmvvm.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jt.stockquoteshiltmvvm.data.mapper.QuoteMapper
import com.jt.stockquoteshiltmvvm.data.repository.QuoteRepositoryImpl
import com.jt.stockquoteshiltmvvm.data.service.QuoteService
import com.jt.stockquoteshiltmvvm.domain.repository.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://finnhub.io/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideQuoteService(retrofit: Retrofit.Builder): QuoteService {
        return retrofit.build().create(QuoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideQuoteRepository(quoteService: QuoteService, quoteMapper: QuoteMapper) : QuoteRepository {
        return QuoteRepositoryImpl(quoteService, quoteMapper)
    }
}