package com.natuan.currencyconverterapp.data.remote.response

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */
data class ConversionRate(val code: String, val rate: Double){
    fun getCodeWithoutUSD(): String {
        return code.substring(3)
    }
}
