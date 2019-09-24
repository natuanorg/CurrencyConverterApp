package com.natuan.currencyconverterapp.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by natuanorg on 2019-09-23.
 * natuan.org@gmail.com
 */

class WorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory<out ListenableWorker>>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return try {
            val foundEntry =
                workerFactories.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
            val factory = foundEntry?.value
                ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")
            factory.get().create(workerParameters)
        } catch (ignore: Exception) {
            null
        }
    }
}