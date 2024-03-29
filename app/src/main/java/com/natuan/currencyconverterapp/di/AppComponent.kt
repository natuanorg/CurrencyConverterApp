package com.natuan.currencyconverterapp.di

import android.app.Application
import com.natuan.currencyconverterapp.CurrencyConverterApp
import com.natuan.currencyconverterapp.di.module.AppModule
import com.natuan.currencyconverterapp.di.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(currencyConverterApp: CurrencyConverterApp)
}