package com.natuan.currencyconverterapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
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

    fun showLoading() {
        showDialogFragment(LoadingDialogFragment.newInstance(), LoadingDialogFragment.TAG)
    }

    fun hideLoading() {
        hideDialogFragment(LoadingDialogFragment.TAG)
    }

    fun showDialogFragment(dialogFragment: DialogFragment, tag: String) {
        hideDialogFragment(tag)
        fragmentManager?.let { dialogFragment.show(it, tag) }
    }

    fun hideDialogFragment(tag: String) {
        fragmentManager?.findFragmentByTag(tag)?.let {
            (it as DialogFragment).dismiss()
        }
    }
}

