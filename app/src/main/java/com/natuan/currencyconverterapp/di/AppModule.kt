package com.natuan.currencyconverterapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */

@Module(includes = [ViewModelModule::class, NetworkModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}