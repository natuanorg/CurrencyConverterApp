package com.natuan.currencyconverterapp.helper

import okhttp3.ResponseBody

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */
data class Resource<out T>(val status: Status, val data: T?, val responseBody: ResponseBody? = null, val message: String){
    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, message= "success")
        }

        fun <T> error(data: T?, responseBody: ResponseBody?, msg: String): Resource<T> {
            return Resource(Status.ERROR, data, responseBody= responseBody, message= msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data = data, message = "loading")
        }
    }
}