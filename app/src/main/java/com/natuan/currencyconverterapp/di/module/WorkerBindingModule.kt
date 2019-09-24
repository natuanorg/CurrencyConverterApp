package com.natuan.currencyconverterapp.di.module

import androidx.work.ListenableWorker
import com.natuan.currencyconverterapp.di.worker.ChildWorkerFactory
import com.natuan.currencyconverterapp.di.worker.WorkerKey
import com.natuan.currencyconverterapp.worker.CurrencyWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by natuanorg on 2019-09-23.
 * natuan.org@gmail.com
 */
@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(CurrencyWorker::class)
    fun bindCurrencyWorker(factory: CurrencyWorker.Factory) : ChildWorkerFactory<out ListenableWorker>
}