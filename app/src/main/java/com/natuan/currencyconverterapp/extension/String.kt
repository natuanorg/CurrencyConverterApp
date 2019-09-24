package com.natuan.currencyconverterapp.extension

/**
 * Created by natuanorg on 2019-09-23.
 * natuan.org@gmail.com
 */

fun String.extractDouble(): Double? {
    if (this.isEmpty()) return 0.0
    return this.replace("[^A-Za-z0-9 ]".toRegex(),"").trim().toDouble()
}
