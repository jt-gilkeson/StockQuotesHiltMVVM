package com.jt.stockquoteshiltmvvm.data.mapper

import com.jt.stockquoteshiltmvvm.data.dto.QuoteDto
import com.jt.stockquoteshiltmvvm.domain.model.Quote
import javax.inject.Inject

class QuoteMapper @Inject constructor() {
    fun map(quoteDto: QuoteDto): Quote {
        return Quote(quoteDto.current)
    }
}