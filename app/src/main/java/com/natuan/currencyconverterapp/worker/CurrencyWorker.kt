package com.natuan.currencyconverterapp.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.natuan.currencyconverterapp.di.worker.ChildWorkerFactory
import com.natuan.currencyconverterapp.repository.CurrencyConverterRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by natuanorg on 2019-09-23.
 * natuan.org@gmail.com
 */
class CurrencyWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val converterRepository: CurrencyConverterRepository
) : Worker(context, workerParams) {

    companion object {
        const val uniqueWorkerName = "get_latest_currency"
    }

    override fun doWork(): Result {
        Timber.d("CurrencyWorker.doWork")
        converterRepository.updateCurrencies()
        return Result.success()
    }

    class Factory @Inject constructor(
        private val context: Provider<Context>,
        private val converterRepository: CurrencyConverterRepository
    ) : ChildWorkerFactory<CurrencyWorker> {
        override fun create(params: WorkerParameters): CurrencyWorker {
            return CurrencyWorker(context.get(), params, converterRepository)
        }
    }
}

