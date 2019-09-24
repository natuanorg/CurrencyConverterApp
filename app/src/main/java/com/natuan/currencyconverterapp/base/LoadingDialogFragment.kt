package com.natuan.currencyconverterapp.base

import android.view.View
import com.natuan.currencyconverterapp.R

/**
 * Created by natuanorg on 2019-09-24.
 * natuan.org@gmail.com
 */

class LoadingDialogFragment : BaseDialogFragment() {

    companion object {
        const val TAG = "LoadingDialogFragment"
        fun newInstance() : LoadingDialogFragment{
            return LoadingDialogFragment()
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_loading_dialog
    }

    override fun initViews(view: View) {
        
    }
}