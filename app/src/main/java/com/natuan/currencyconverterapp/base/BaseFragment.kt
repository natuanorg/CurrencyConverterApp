package com.natuan.currencyconverterapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.natuan.currencyconverterapp.di.Injectable
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by natuanorg on 2019-09-18.
 * natuan.org@gmail.com
 */

abstract class BaseFragment : SupportFragment(), Injectable {
    abstract fun getLayoutResource(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }
}

