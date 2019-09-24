package com.natuan.currencyconverterapp.base

import android.view.View
import com.natuan.currencyconverterapp.R
import kotlinx.android.synthetic.main.dialog_retry.*

/**
 * Created by natuanorg on 2019-09-24.
 * natuan.org@gmail.com
 */

class RetryDialog(
    val title: String = "Message",
    val content: String,
    var retryFunc: (() -> Unit?)? = null
) : BaseDialogFragment() {

    companion object {
        const val TAG = "RetryDialog"
    }

    override fun getLayoutResource(): Int {
        return R.layout.dialog_retry
    }

    override fun initViews(view: View) {
        message_dialog_title.setText(title).takeIf { title.isNotEmpty() }
        message_dialog_content.setText(content).takeIf { content.isNotEmpty() }
        close_btn.setOnClickListener {
            retryFunc?.let { it1 -> it1() }
            dismiss()
        }
    }
}