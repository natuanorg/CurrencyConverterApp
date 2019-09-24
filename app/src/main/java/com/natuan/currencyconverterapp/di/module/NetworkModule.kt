package com.natuan.currencyconverterapp.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.natuan.currencyconverterapp.BuildConfig
import com.natuan.currencyconverterapp.data.remote.CurrencyLayerService
import com.natuan.currencyconverterapp.data.remote.RequestInterceptor
import com.natuan.currencyconverterapp.helper.LiveDataCallAdapterFactory
import com.natuan.currencyconverterapp.helper.RatesFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */
@Module
class NetworkModule {

    private val END_POINT = BuildConfig.END_POINT

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(RatesFactory()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(END_POINT)
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor).addNetworkInterceptor(StethoInterceptor())
        }
        okHttpClientBuilder.addInterceptor(RequestInterceptor())
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideCurrencyLayerService(retrofit: Retrofit): CurrencyLayerService = retrofit.create(CurrencyLayerService::class.java)
}