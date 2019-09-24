package com.natuan.currencyconverterapp.repository

import androidx.lifecycle.LiveData
import com.natuan.currencyconverterapp.data.local.db.ConversionRateDao
import com.natuan.currencyconverterapp.data.local.db.CurrencyConverterDb
import com.natuan.currencyconverterapp.data.local.db.entity.ConversionRateEntity
import com.natuan.currencyconverterapp.data.local.shared.AppPrefs
import com.natuan.currencyconverterapp.data.remote.CurrencyLayerService
import com.natuan.currencyconverterapp.data.remote.response.RatesResponse
import com.natuan.currencyconverterapp.helper.ApiResponse
import com.natuan.currencyconverterapp.helper.ApiSuccessResponse
import com.natuan.currencyconverterapp.helper.AppExecutors
import com.natuan.currencyconverterapp.helper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by natuanorg on 2019-09-20.
 * natuan.org@gmail.com
 */

class CurrencyConverterRepository @Inject constructor(
    val appExecutors: AppExecutors,
    private val currencyLayerService: CurrencyLayerService,
    private val currencyConverterDb: CurrencyConverterDb,
    val conversionRateDao: ConversionRateDao,
    private val appPrefs: AppPrefs
) {

    fun calculate(input: CurrencyConverterParams): LiveData<Resource<Double>> {
        return object : CurrencyConverterResource(appExecutors, input.amount) {

            override fun loadBaseSource(): LiveData<List<ConversionRateEntity>> {
                return conversionRateDao.findByCode("USD${input.baseCurrency}")
            }

            override fun loadTargetSource(): LiveData<List<ConversionRateEntity>> {
                return conversionRateDao.findByCode("USD${input.targetCurrency}")
            }

        }.asLiveData()
    }

    fun getCurrencies() : LiveData<Resource<List<ConversionRateEntity>>> {
        return object: NetworkBoundResource<List<ConversionRateEntity>, RatesResponse>(appExecutors){
            override fun saveCallResult(response: ApiResponse<RatesResponse>) {
                if (response is ApiSuccessResponse) {
                    val ratesResponse = response.body
                    if (ratesResponse.success) {
                        appPrefs.setLastUpdate(ratesResponse.timestamp)
                        currencyConverterDb.runInTransaction {
                            conversionRateDao.insert(ratesResponse.quotes.rates.map {
                                ConversionRateEntity.fromResponse(it)
                            })
                        }
                    }
                }
            }

            override fun shouldFetch(): Boolean {
                return appPrefs.getLastUpdate() == 0
            }

            override fun loadFromDb(): LiveData<List<ConversionRateEntity>> {
                return conversionRateDao.finAll()
            }

            override fun createCall(): LiveData<ApiResponse<RatesResponse>> {
                return currencyLayerService.getCurrencies()
            }

        }.asLiveData()
    }

    fun updateCurrencies() {
        appExecutors.networkIO().execute {
            currencyLayerService.updateCurrencies().enqueue(object : Callback<RatesResponse> {
                override fun onResponse(call: Call<RatesResponse>, response: Response<RatesResponse>) {
                    handleCurrenciesResponse(response)
                }

                override fun onFailure(call: Call<RatesResponse>, t: Throwable) {
                    Timber.d(t)
                }
            })
        }
    }

    fun handleCurrenciesResponse(response: Response<RatesResponse>) {
        val ratesResponse = response.body()
        if (response.isSuccessful && ratesResponse != null ) {
            appPrefs.setLastUpdate(ratesResponse.timestamp)
            appExecutors.diskIO().execute {
                currencyConverterDb.runInTransaction {
                    conversionRateDao.insert(
                        ratesResponse.quotes.rates.map {
                            ConversionRateEntity.fromResponse(it)
                        }
                    )
                }
            }
        }
    }
}