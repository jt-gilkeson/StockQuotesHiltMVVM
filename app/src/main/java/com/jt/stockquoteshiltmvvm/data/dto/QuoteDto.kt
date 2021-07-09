package com.jt.stockquoteshiltmvvm.data.dto
import com.google.gson.annotations.SerializedName


data class QuoteDto(
    @SerializedName("c")
    val current: Double,
    @SerializedName("h")
    val high: Double,
    @SerializedName("l")
    val low: Double,
    @SerializedName("o")
    val open: Double,
    @SerializedName("pc")
    val previous: Double,
    @SerializedName("t")
    val time: Int
)