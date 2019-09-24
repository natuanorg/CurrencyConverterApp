package com.natuan.currencyconverterapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.natuan.currencyconverterapp.R
import com.natuan.currencyconverterapp.data.model.Currency
import kotlinx.android.synthetic.main.item_supported_currency.view.*

/**
 * Created by natuanorg on 2019-09-21.
 * natuan.org@gmail.com
 */

class SupportedCurrencyAdapter constructor(
    private var listener: ItemClickListener
) : RecyclerView.Adapter<SupportedCurrencyAdapter.SupportedCurrencyViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(code: String)
    }

    private var dataSet = emptyList<Currency>()

    fun updateDataSet(data: List<Currency>) {
        dataSet = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportedCurrencyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_supported_currency, parent, false)
        return SupportedCurrencyViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: SupportedCurrencyViewHolder, position: Int) {
        val currency = dataSet[position]
        holder.bind(currency)
    }

    class SupportedCurrencyViewHolder(itemView: View, private val listener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: Currency) {
            itemView.code_tv.text = currency.code
            itemView.name_tv.text = currency.name
            itemView.setOnClickListener {
                listener.onItemClick(currency.code)
            }
        }
    }

}