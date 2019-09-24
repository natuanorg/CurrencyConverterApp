package com.natuan.currencyconverterapp.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.natuan.currencyconverterapp.R
import kotlinx.android.synthetic.main.merge_action_bar_view.view.*

/**
 * Created by natuanorg on 2019-09-21.
 * natuan.org@gmail.com
 */
class ActionBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    interface ActionBarListener {
        fun onActionBarItemSelected(view: View)
    }

    var actionBarListener: ActionBarListener? = null
        set(value) {
            field = value
            field?.let {
                left_btn.setOnClickListener { view -> it.onActionBarItemSelected(view) }
                right_btn.setOnClickListener { view -> it.onActionBarItemSelected(view) }
            }
        }

    init {
        inflate(context, R.layout.merge_action_bar_view, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ActionBarView)
        title_tv.text = attributes.getString(R.styleable.ActionBarView_title)

        val titleColor = attributes.getColor(R.styleable.ActionBarView_titleColor,
            ContextCompat.getColor(getContext(), R.color.white))
        title_tv.setTextColor(titleColor)

        val backgroundColor = attributes.getColor(R.styleable.ActionBarView_backgroundColor,
            ContextCompat.getColor(getContext(), R.color.white))
        setBackgroundColor(backgroundColor)

        left_btn.setImageDrawable(attributes.getDrawable(R.styleable.ActionBarView_drawableLeft))
        right_btn.setImageDrawable(attributes.getDrawable(R.styleable.ActionBarView_drawableRight))

        attributes.recycle()
    }
}