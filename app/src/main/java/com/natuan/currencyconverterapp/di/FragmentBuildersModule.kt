package com.natuan.currencyconverterapp.di

import com.natuan.currencyconverterapp.ui.CurrencyConverterFragment
import com.natuan.currencyconverterapp.ui.SupportedCurrencyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeConversionRateFragment(): CurrencyConverterFragment

    @ContributesAndroidInjector
    abstract fun contributeSupportedCurrencyFragment(): SupportedCurrencyFragment

}