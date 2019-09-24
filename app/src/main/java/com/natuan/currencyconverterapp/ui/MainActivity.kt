package com.natuan.currencyconverterapp.ui

import android.os.Bundle
import com.natuan.currencyconverterapp.R
import com.natuan.currencyconverterapp.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initViews(savedInstanceState: Bundle?) {
        if (findFragment(CurrencyConverterFragment::class.java) == null) {
            loadRootFragment(R.id.container, CurrencyConverterFragment.newInstance())
        }
    }
}
