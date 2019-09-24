package com.natuan.currencyconverterapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.natuan.currencyconverterapp.data.local.db.entity.ConversionRateEntity

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */
@Database(
    entities = [
        ConversionRateEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CurrencyConverterDb : RoomDatabase() {
    abstract fun conversionRateDao(): ConversionRateDao
}