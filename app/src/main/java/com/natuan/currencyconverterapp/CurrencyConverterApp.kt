package com.natuan.currencyconverterapp

import android.app.Activity
import android.app.Application
import android.app.Service
import com.facebook.stetho.Stetho
import com.natuan.currencyconverterapp.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import me.yokeyword.fragmentation.Fragmentation
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by natuanorg on 2019-09-18.
 * natuan.org@gmail.com
 */
class CurrencyConverterApp : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Stetho.initializeWithDefaults(this);

        Fragmentation.builder()
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(true)
            .install()

        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

    override fun serviceInjector() = dispatchingServiceInjector
}