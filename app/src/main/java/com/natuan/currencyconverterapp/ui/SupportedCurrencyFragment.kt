package com.natuan.currencyconverterapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.natuan.currencyconverterapp.R
import com.natuan.currencyconverterapp.base.BaseFragment
import com.natuan.currencyconverterapp.data.model.Currency
import com.natuan.currencyconverterapp.data.model.SupportedCurrency
import com.natuan.currencyconverterapp.extension.hideKeyboard
import com.natuan.currencyconverterapp.ui.adapter.SupportedCurrencyAdapter
import com.natuan.currencyconverterapp.ui.customview.ActionBarView
import kotlinx.android.synthetic.main.fragment_supported_currency.*

/**
 * Created by natuanorg on 2019-09-21.
 * natuan.org@gmail.com
 */
class SupportedCurrencyFragment constructor(
    private var requestType: String,
    private var listener: SelectedCurrencyListener
) : BaseFragment(), ActionBarView.ActionBarListener,
    SupportedCurrencyAdapter.ItemClickListener {

    interface SelectedCurrencyListener {
        fun onSelectedCurrency(requestType: String, code: String)
    }

    companion object {
        const val TYPE_BASE = "BASE"
        const val TYPE_TARGET = "TARGET"
        fun newInstance(
            requestType: String,
            listener: SelectedCurrencyListener
        ): SupportedCurrencyFragment {
            return SupportedCurrencyFragment(requestType, listener)
        }
    }

    private lateinit var supportedCurrencyAdapter: SupportedCurrencyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var originalList = listOf<Currency>()
    private var filteredList = listOf<Currency>()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_supported_currency
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action_bar.actionBarListener = this
        search_edt.addTextChangedListener(textChangedListener)
    }

    private val textChangedListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val keyword = p0.toString()
            filteredList = originalList.filter {
                it.code.contains(keyword, true) || it.name.contains(keyword, true)
            }
            (if(filteredList.isNotEmpty()) filteredList else originalList).let {
                supportedCurrencyAdapter.updateDataSet(it)
            }
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        supportedCurrencyAdapter = SupportedCurrencyAdapter(this)
        viewManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        currency_rv.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = supportedCurrencyAdapter
            addItemDecoration(dividerItemDecoration)
        }
        originalList = SupportedCurrency.list()
        supportedCurrencyAdapter.updateDataSet(originalList)

        currency_rv.setOnTouchListener { v, event ->
            v.hideKeyboard()
            false
        }
    }

    override fun onActionBarItemSelected(view: View) {
        when (view.id) {
            R.id.left_btn -> {
                view.hideKeyboard()
                pop()
            }
        }
    }

    override fun onItemClick(code: String) {
        listener.onSelectedCurrency(requestType, code)
        view?.hideKeyboard()
        pop()
    }
}