package com.natuan.currencyconverterapp.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.natuan.currencyconverterapp.data.local.db.entity.ConversionRateEntity
import com.natuan.currencyconverterapp.data.local.shared.AppPrefs
import com.natuan.currencyconverterapp.extension.extractDouble
import com.natuan.currencyconverterapp.helper.Resource
import com.natuan.currencyconverterapp.repository.CurrencyConverterParams
import com.natuan.currencyconverterapp.repository.CurrencyConverterRepository
import com.natuan.currencyconverterapp.worker.CurrencyWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */
class CurrencyConverterViewModel @Inject constructor(
    private val conversionRateRepository: CurrencyConverterRepository,
    private val context: Context,
    private val appPrefs: AppPrefs
) : ViewModel() {

    private val _amount: MutableLiveData<String> = MutableLiveData()
    val amount: LiveData<String> get() = _amount

    private val _params: MutableLiveData<CurrencyConverterParams> = MutableLiveData()

    fun getCurrencies() : LiveData<Resource<List<ConversionRateEntity>>> {
        return conversionRateRepository.getCurrencies()
    }

    fun output() : LiveData<Resource<Double>> {
        return Transformations.switchMap(_params) { input ->
            conversionRateRepository.calculate(input)
        }
    }

    fun updateInput(value: String) {
        val currentInput = _amount.value ?: ""

        if (currentInput.isEmpty() && (value === "0" || value === "x")) { return }

        _amount.value = when(value){
            "x" -> { if(currentInput.length > 1) currentInput.substring(0, currentInput.length - 1) else "" }
            else -> { "$currentInput$value" }
        }

        setParams(_amount.value)
    }

    fun setParams(amountStr: String?) {
        val baseCurrency = appPrefs.getBaseCurrencyCode()
        val targetCurrency = appPrefs.getTargetCurrencyCode()
        try {
            val amount = amountStr?.extractDouble()
            Timber.d("amount: $amount")
            amount?.let {
                val update = CurrencyConverterParams(it, baseCurrency, targetCurrency)
                if (_params.value == update) { return }
                _params.value = update
            }
        } catch (exception: Exception) {
            Timber.d(exception)
        }
    }

    fun setupUpdate() {
        Timber.d("setupUpdate")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED).build()

        val currencyWorker =
            PeriodicWorkRequest.Builder(CurrencyWorker::class.java, 30, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            CurrencyWorker.uniqueWorkerName,
            ExistingPeriodicWorkPolicy.KEEP,
            currencyWorker
        )
        WorkManager.getInstance(context).enqueue(currencyWorker)
    }
}