package com.natuan.currencyconverterapp.data.local.shared

import android.content.Context
import android.content.SharedPreferences
import com.natuan.currencyconverterapp.extension.get
import com.natuan.currencyconverterapp.extension.set
import javax.inject.Inject

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */
class AppPrefs @Inject constructor(context: Context) {

    var mPrefs : SharedPreferences = context.getSharedPreferences(PrefsConstant.FILE_NAME, Context.MODE_PRIVATE)

    fun setLastUpdate(timestamp: Int) {
        mPrefs[PrefsConstant.LAST_UPDATE] = timestamp
    }

    fun getLastUpdate(): Int {
        return mPrefs.get(PrefsConstant.LAST_UPDATE, 0) ?: 0
    }

    fun setBaseCurrencyCode(value: String) {
        mPrefs[PrefsConstant.BASE_CURRENCY] = value
    }

    fun getBaseCurrencyCode(): String {
        return mPrefs.get(PrefsConstant.BASE_CURRENCY, "USD") ?: "USD"
    }

    fun setTargetCurrencyCode(value: String) {
        mPrefs[PrefsConstant.TARGET_CURRENCY] = value
    }

    fun getTargetCurrencyCode(): String {
        return mPrefs.getString(PrefsConstant.TARGET_CURRENCY, "VND") ?: "VND"
    }

    inline fun <reified T : Any> get(pref: String, defaultValue: T? = null): T? {
        return mPrefs.get(pref, defaultValue)
    }

    inline fun <reified T : Any> set(pref: String, value: T?) {
        mPrefs[pref] = value
    }
}

