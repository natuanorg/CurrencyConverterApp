package com.natuan.currencyconverterapp.helper

import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */

internal const val UNKNOWN_CODE = -1

sealed class ApiResponse<T> {
    companion object {

        fun <T> create(errorCode: Int, error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(errorCode, error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                Timber.d(response.errorBody()?.source().toString())
                ApiErrorResponse(response.code(), responseBody = response.errorBody())
            }
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(
    val errorCode: Int,
    val errorMessage: String = "",
    val responseBody: ResponseBody? = null
) : ApiResponse<T>()