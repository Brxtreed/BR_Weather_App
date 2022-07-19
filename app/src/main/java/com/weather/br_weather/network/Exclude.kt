package com.weather.br_weather.network

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.annotations.SerializedName
import javax.inject.Inject


class Exclude @Inject constructor() : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }

    override fun shouldSkipField(field: FieldAttributes?): Boolean {
        val ns = field?.getAnnotation(SerializedName::class.java)
        return ns == null
    }
}