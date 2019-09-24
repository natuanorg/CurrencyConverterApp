package com.natuan.currencyconverterapp.data.remote

import androidx.lifecycle.LiveData
import com.natuan.currencyconverterapp.data.model.SupportedCurrency
import com.natuan.currencyconverterapp.data.remote.response.RatesResponse
import com.natuan.currencyconverterapp.helper.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */

interface CurrencyLayerService {

    @GET("/live")
    fun getCurrencies(
        @Query("source") source: String = SupportedCurrency.DEFAULT,
        @Query("currencies") currencies: String = SupportedCurrency.CURRENCIES
    ) : LiveData<ApiResponse<RatesResponse>>

    @GET("/live")
    fun updateCurrencies(
        @Query("source") source: String = SupportedCurrency.DEFAULT,
        @Query("currencies") currencies: String = SupportedCurrency.CURRENCIES,
        @Query("format") format: Int = 1
    ) : Call<RatesResponse>


}