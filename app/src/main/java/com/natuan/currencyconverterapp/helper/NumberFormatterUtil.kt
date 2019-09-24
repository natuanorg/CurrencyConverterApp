package com.natuan.currencyconverterapp.helper

import com.natuan.currencyconverterapp.extension.toComma

class NumberFormatterUtil {
    companion object {
        fun addComma(s: String): String {
            if (s.isEmpty()) return ""
            val value = s.replace("[^A-Za-z0-9 ]".toRegex(),"")
            val dotIndex = value.indexOf('.')
            return if (dotIndex == -1) {
                value.toDouble().toComma()
            } else {
                value.substring(0, dotIndex).toDouble().toComma() + value.substring(dotIndex)
            }
        }
    }
}
