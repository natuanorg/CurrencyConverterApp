package com.natuan.currencyconverterapp.extension

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */

fun Double.toComma(): String {
    val decimalFormat = DecimalFormat("#,###.#######################")
    decimalFormat.decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
    val bigDecimal = this.toBigDecimal()
    return decimalFormat.format(bigDecimal)
}

fun Double.toTwoDecimalWithComma(): String {
    val decimalFormat = DecimalFormat("#,###.##")
    decimalFormat.decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
    val bigDecimal = this.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
    return decimalFormat.format(bigDecimal)
}
