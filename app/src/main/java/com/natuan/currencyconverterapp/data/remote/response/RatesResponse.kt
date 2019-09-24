package com.natuan.currencyconverterapp.data.remote.response

data class RatesResponse(
    val privacy: String,
    val quotes: Quotes,
    val source: String,
    val success: Boolean,
    val terms: String,
    val timestamp: Int
)