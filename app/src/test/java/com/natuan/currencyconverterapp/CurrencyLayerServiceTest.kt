package com.natuan.currencyconverterapp

import com.natuan.currencyconverterapp.data.remote.CurrencyLayerService
import com.natuan.currencyconverterapp.data.remote.response.RatesResponse
import com.natuan.currencyconverterapp.helper.ApiSuccessResponse
import com.natuan.currencyconverterapp.helper.LiveDataCallAdapterFactory
import com.natuan.currencyconverterapp.helper.RatesFactory
import com.natuan.currencyconverterapp.util.InstantExecutorExtension
import com.natuan.currencyconverterapp.util.LiveDataTestUtil.getValue
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.Java6Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */

@ExtendWith(InstantExecutorExtension::class)
class CurrencyLayerServiceTest  {

    private lateinit var service : CurrencyLayerService

    @BeforeEach
    fun createService() {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        val okHttpLogging = HttpLoggingInterceptor()
        okHttpLogging.level = HttpLoggingInterceptor.Level.BODY

        val client = builder.addInterceptor(okHttpLogging)
            .addInterceptor {
                var request = it.request()
                val url = request.url().newBuilder()
                    .addQueryParameter("access_key", BuildConfig.ACCESS_KEY).build()
                request = request.newBuilder().url(url).build()
                it.proceed(request)
            }
            .build()

        service = Retrofit.Builder()
            .baseUrl(BuildConfig.END_POINT)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(RatesFactory()).build()))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build().create(CurrencyLayerService::class.java)
    }

    @Test
    fun live() {
        val response = (getValue(service.getCurrencies()) as ApiSuccessResponse<RatesResponse>).body
        val quotes = response.quotes
        Java6Assertions.assertThat(quotes).isNotNull
    }
}