package com.natuan.currencyconverterapp.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.natuan.currencyconverterapp.R
import kotlinx.android.synthetic.main.merge_keyboard_view.view.*

/**
 * Created by natuanorg on 2019-09-23.
 * natuan.org@gmail.com
 */
class KeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    interface KeyboardViewListener {
        fun onKeyboardPress(value: String)
    }

    var keyboardViewListener : KeyboardViewListener? = null

    init {
        inflate(context, R.layout.merge_keyboard_view, this)
        orientation = VERTICAL

        val keys = listOf(
            key_1,key_2,key_3,key_4,key_5,key_6,
            key_7,key_8,key_9,key_dot,key_0,key_x
        )

        keys.forEach {
            it.setOnClickListener { textView ->
                val value = (textView as TextView).text.toString()
                if (value.isNotEmpty() && keyboardViewListener != null) {
                    keyboardViewListener!!.onKeyboardPress(value)
                }
            }
        }
    }
}