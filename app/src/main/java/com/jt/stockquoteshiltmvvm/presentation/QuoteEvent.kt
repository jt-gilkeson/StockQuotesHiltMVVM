package com.jt.stockquoteshiltmvvm.presentation

sealed class QuoteEvent {
    data class GetQuoteEvent(val symbol: String) : QuoteEvent()
}
