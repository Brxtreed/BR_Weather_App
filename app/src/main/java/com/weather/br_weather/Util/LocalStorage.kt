package com.weather.br_weather.Util

interface LocalStorage {
        fun saveString(preferenceName: String, value: String)
        fun readString(preferenceName: String): String?
        fun clearPreference(preferenceName: String)
        fun readBoolean(preferenceName: String): Boolean
        fun saveBoolean(preferenceName: String, value: Boolean)
    }
