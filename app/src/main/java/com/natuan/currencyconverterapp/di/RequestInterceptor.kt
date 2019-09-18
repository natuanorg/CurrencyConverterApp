package com.natuan.currencyconverterapp.di

import com.natuan.currencyconverterapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.HttpUrl



/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("access_key", BuildConfig.ACCESS_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}