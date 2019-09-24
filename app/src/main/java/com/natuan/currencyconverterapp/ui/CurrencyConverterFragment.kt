package com.natuan.currencyconverterapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.natuan.currencyconverterapp.R
import com.natuan.currencyconverterapp.base.BaseFragment
import com.natuan.currencyconverterapp.base.RetryDialog
import com.natuan.currencyconverterapp.data.local.db.entity.ConversionRateEntity
import com.natuan.currencyconverterapp.data.local.shared.AppPrefs
import com.natuan.currencyconverterapp.extension.toTwoDecimalWithComma
import com.natuan.currencyconverterapp.helper.NumberFormatterUtil
import com.natuan.currencyconverterapp.helper.Resource
import com.natuan.currencyconverterapp.helper.Status
import com.natuan.currencyconverterapp.ui.SupportedCurrencyFragment.Companion.TYPE_BASE
import com.natuan.currencyconverterapp.ui.SupportedCurrencyFragment.Companion.TYPE_TARGET
import com.natuan.currencyconverterapp.ui.customview.KeyboardView
import kotlinx.android.synthetic.main.fragment_currency_converter.*
import javax.inject.Inject

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */
class CurrencyConverterFragment : BaseFragment(),
    SupportedCurrencyFragment.SelectedCurrencyListener,
    View.OnClickListener, KeyboardView.KeyboardViewListener {

    companion object {
        fun newInstance(): CurrencyConverterFragment {
            return CurrencyConverterFragment()
        }
    }

    @Inject
    lateinit var appPrefs: AppPrefs

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val conversionRateViewModel: CurrencyConverterViewModel by viewModels {
        viewModelFactory
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_currency_converter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        base_btn.text = appPrefs.getBaseCurrencyCode()
        target_btn.text = appPrefs.getTargetCurrencyCode()

        keyboard.keyboardViewListener = this
        base_btn.setOnClickListener(this)
        target_btn.setOnClickListener(this)
        swap_btn.setOnClickListener(this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getCurrencies()

        setupUpdate()

        conversionRateViewModel.output().observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                target_tv.text = it.data?.toTwoDecimalWithComma() ?: ""
            }
        })

        conversionRateViewModel.amount.observe(viewLifecycleOwner, Observer {
            it?.let {
                base_tv.text = NumberFormatterUtil.addComma(it)
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.base_btn -> { start(SupportedCurrencyFragment.newInstance(TYPE_BASE, this)) }
            R.id.target_btn -> { start(SupportedCurrencyFragment.newInstance(TYPE_TARGET, this)) }
            R.id.swap_btn -> { swap() }
        }
    }

    override fun onSelectedCurrency(requestType: String, code: String) {
        when (requestType) {
            TYPE_BASE -> { appPrefs.setBaseCurrencyCode(code) }
            TYPE_TARGET -> { appPrefs.setTargetCurrencyCode(code) }
        }
        convert()
    }

    override fun onKeyboardPress(value: String) {
        conversionRateViewModel.updateInput(value)
    }

    private fun getCurrencies() {
        conversionRateViewModel.getCurrencies().observe(viewLifecycleOwner, Observer {
            handleCurrenciesResponse(it)
        })
    }

    private fun setupUpdate() {
        conversionRateViewModel.setupUpdate()
    }

    private fun handleCurrenciesResponse(response: Resource<List<ConversionRateEntity>>) {
        when (response.status) {
            Status.LOADING -> {
                showLoading()
            }
            Status.ERROR -> {
                hideLoading()
                showDialogFragment(RetryDialog(
                    title = "Message",
                    content = "Cannot access real-time exchange rates, please check your network connection",
                    retryFunc = {
                        this.getCurrencies()
                    }
                ), RetryDialog.TAG)
            }
            Status.SUCCESS -> {
                hideLoading()
            }
        }
    }

    private fun swap() {

        val baseCurrency = appPrefs.getBaseCurrencyCode()
        val targetCurrency = appPrefs.getTargetCurrencyCode()

        appPrefs.setBaseCurrencyCode(targetCurrency)
        appPrefs.setTargetCurrencyCode(baseCurrency)

        convert()
    }

    private fun convert() {

        base_btn.text = appPrefs.getBaseCurrencyCode()
        target_btn.text = appPrefs.getTargetCurrencyCode()

        conversionRateViewModel.setParams(base_tv.text.toString())
    }
}