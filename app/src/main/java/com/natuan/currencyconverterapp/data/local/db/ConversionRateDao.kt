package com.natuan.currencyconverterapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.natuan.currencyconverterapp.data.local.db.entity.ConversionRateEntity

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */
@Dao
abstract class ConversionRateDao : BaseDao<ConversionRateEntity>{

    @Query("SELECT * FROM conversion_rates")
    abstract fun finAll(): LiveData<List<ConversionRateEntity>>

    @Query("SELECT * FROM conversion_rates WHERE code=:currencyCode ORDER BY code ASC")
    abstract fun findByCode(currencyCode: String): LiveData<List<ConversionRateEntity>>
}