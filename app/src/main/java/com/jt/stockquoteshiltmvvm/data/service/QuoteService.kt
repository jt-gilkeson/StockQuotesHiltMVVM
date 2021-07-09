package com.jt.stockquoteshiltmvvm.data.service

import com.jt.stockquoteshiltmvvm.data.dto.QuoteDto
import retrofit2.http.GET

import retrofit2.http.Query

interface QuoteService {
    @GET("quote?token=c3jqbpiad3i82raokje0")
    suspend fun getQuote(@Query("symbol") symbol: String): QuoteDto
}
