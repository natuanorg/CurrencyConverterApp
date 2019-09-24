package com.natuan.currencyconverterapp.helper

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import com.natuan.currencyconverterapp.data.remote.response.Quotes
import com.natuan.currencyconverterapp.data.remote.response.ConversionRate
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

/**
 * Created by natuanorg on 2019-09-19.
 * natuan.org@gmail.com
 */
class QuotesCustomAdapter : JsonAdapter<Quotes>() {
    override fun fromJson(reader: JsonReader): Quotes? {

        val list = mutableListOf<ConversionRate>()

        reader.beginObject()
        while (reader.hasNext()) {
            list.add(
                ConversionRate(
                    code = reader.nextName(),
                    rate = reader.nextString().toDouble()
                )
            )
        }
        reader.endObject()
        return Quotes().apply {
            rates = list
        }
    }

    override fun toJson(writer: JsonWriter, value: Quotes?) {

    }
}

class RatesFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        if (Types.getRawType(type).isAssignableFrom(Quotes::class.java)) {
            return QuotesCustomAdapter()
        }
        return null
    }

}