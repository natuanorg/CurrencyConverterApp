package com.natuan.currencyconverterapp.di.module

import android.app.Application
import android.content.Context
import com.natuan.currencyconverterapp.data.local.shared.AppPrefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */

@Module(includes = [ViewModelModule::class, NetworkModule::class, RoomModule::class, WorkerBindingModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideAppPrefs(context: Context): AppPrefs {
        return AppPrefs(context)
    }
}