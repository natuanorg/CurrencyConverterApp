package com.natuan.currencyconverterapp.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import me.yokeyword.fragmentation.SupportActivity
import javax.inject.Inject

/**
 * Created by natuanorg on 2019-09-18.
 * natuan.org@gmail.com
 */
abstract class BaseActivity : SupportActivity(), HasSupportFragmentInjector {

    abstract fun getLayoutResource() : Int

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
    }
}

