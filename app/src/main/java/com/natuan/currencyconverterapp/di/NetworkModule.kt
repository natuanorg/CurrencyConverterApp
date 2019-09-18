package com.natuan.currencyconverterapp.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.natuan.currencyconverterapp.BuildConfig
import com.natuan.currencyconverterapp.data.remote.CurrencyLayerService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */
@Module
class NetworkModule {

    private val END_POINT = "${BuildConfig.END_POINT}"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(END_POINT)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
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