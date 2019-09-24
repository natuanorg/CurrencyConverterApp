package com.natuan.currencyconverterapp.di.worker

import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

/**
 * Created by natuanorg on 2019-09-23.
 * natuan.org@gmail.com
 */
interface ChildWorkerFactory<T : ListenableWorker> {
    fun create(params: WorkerParameters): T
}