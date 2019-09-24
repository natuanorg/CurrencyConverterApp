package com.natuan.currencyconverterapp.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.natuan.currencyconverterapp.data.model.SupportedCurrency
import com.natuan.currencyconverterapp.data.remote.response.ConversionRate

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */

@Entity(tableName = "conversion_rates")
data class ConversionRateEntity (
    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "rate")
    val rate: Double,

    @ColumnInfo(name = "name")
    val name: String
){
    companion object {
        fun fromResponse(conversionRate: ConversionRate): ConversionRateEntity {
            return ConversionRateEntity(
                conversionRate.code,
                conversionRate.rate,
                SupportedCurrency.ALL[conversionRate.getCodeWithoutUSD()] ?: ""
            )
        }
    }
}