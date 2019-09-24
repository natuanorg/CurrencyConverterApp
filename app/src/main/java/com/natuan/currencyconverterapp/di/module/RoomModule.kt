package com.natuan.currencyconverterapp.di.module

import android.app.Application
import androidx.room.Room
import com.natuan.currencyconverterapp.data.local.db.ConversionRateDao
import com.natuan.currencyconverterapp.data.local.db.CurrencyConverterDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideCurrencyConverterDb(app: Application): CurrencyConverterDb {
        return Room
            .databaseBuilder(app, CurrencyConverterDb::class.java, "currency_converter.db")
            .fallbackToDestructiveMigration()
            .build()

    }

    @Singleton
    @Provides
    fun provideConversionRateDao(db: CurrencyConverterDb): ConversionRateDao {
        return db.conversionRateDao()
    }
}