package com.jt.stockquoteshiltmvvm.domain.repository

import com.jt.stockquoteshiltmvvm.domain.model.Quote

interface QuoteRepository {
    suspend fun getQuote(symbol: String): Quote
}