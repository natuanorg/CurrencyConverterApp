package com.natuan.currencyconverterapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.natuan.currencyconverterapp.di.viewmodel.ViewModelFactory
import com.natuan.currencyconverterapp.di.viewmodel.ViewModelKey
import com.natuan.currencyconverterapp.ui.CurrencyConverterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyConverterViewModel::class)
    abstract fun bindConversionRateViewModel(conversionRateViewModel: CurrencyConverterViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}