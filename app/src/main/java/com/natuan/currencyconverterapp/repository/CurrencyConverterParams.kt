package com.natuan.currencyconverterapp.repository

/**
 * Created by natuanorg on 2019-09-22.
 * natuan.org@gmail.com
 */

data class CurrencyConverterParams(
    val amount: Double = 0.0,
    val baseCurrency: String = "USD",
    val targetCurrency: String = "VND"
)