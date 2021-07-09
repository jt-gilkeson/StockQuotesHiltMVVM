package com.jt.stockquoteshiltmvvm.data.repository

import com.jt.stockquoteshiltmvvm.data.mapper.QuoteMapper
import com.jt.stockquoteshiltmvvm.data.service.QuoteService
import com.jt.stockquoteshiltmvvm.domain.model.Quote
import com.jt.stockquoteshiltmvvm.domain.repository.QuoteRepository

class QuoteRepositoryImpl(private val quoteService: QuoteService, private val quoteMapper: QuoteMapper): QuoteRepository {
    override suspend fun getQuote(symbol: String): Quote {
        val dto = quoteService.getQuote(symbol)
        return quoteMapper.map(dto)
    }
}