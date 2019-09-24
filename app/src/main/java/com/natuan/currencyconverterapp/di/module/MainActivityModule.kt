package com.natuan.currencyconverterapp.di.module

import com.natuan.currencyconverterapp.di.FragmentBuildersModule
import com.natuan.currencyconverterapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}