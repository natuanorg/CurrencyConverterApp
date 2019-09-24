package com.natuan.currencyconverterapp.data.local.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */

interface BaseDao<E> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: E)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<E>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: E)
}