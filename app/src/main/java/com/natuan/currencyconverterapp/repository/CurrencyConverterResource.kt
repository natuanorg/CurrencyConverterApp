package com.natuan.currencyconverterapp.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.natuan.currencyconverterapp.data.local.db.entity.ConversionRateEntity
import com.natuan.currencyconverterapp.helper.AppExecutors
import com.natuan.currencyconverterapp.helper.Resource

/**
 * Created by natuanorg on 2019-09-22.
 * natuan.org@gmail.com
 */

abstract class CurrencyConverterResource @MainThread constructor(
    private val appExecutors: AppExecutors,
    private val amount: Double
) {

    private val result = MediatorLiveData<Resource<Double>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val baseSource = loadBaseSource()
        @Suppress("LeakingThis")
        val targetSource = loadTargetSource()
        result.addSource(baseSource) { baseList ->
            result.removeSource(baseSource)
            result.addSource(targetSource) { targetList ->
                appExecutors.mainThread().execute {
                    if (baseList.isNotEmpty() && targetList.isNotEmpty()) {
                        setValue(Resource.success(amount * (targetList.first().rate / baseList.first().rate)))
                    } else {
                        setValue(Resource.success(-1.0))
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<Double>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @MainThread
    protected abstract fun loadBaseSource(): LiveData<List<ConversionRateEntity>>

    @MainThread
    protected abstract fun loadTargetSource(): LiveData<List<ConversionRateEntity>>

    fun asLiveData() = result as LiveData<Resource<Double>>
}