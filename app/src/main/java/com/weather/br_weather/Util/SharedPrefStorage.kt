package com.weather.br_weather.Util

import android.content.Context

class SharedPrefStorage(val context: Context) : LocalStorage {

    override fun clearPreference(preferenceName: String) {
        context.getSharedPreferences("WEATHER_PREF", Context.MODE_PRIVATE)
            .edit().remove(preferenceName).commit()
    }

    override fun saveString(preferenceName: String, value: String) {
        context.getSharedPreferences("WEATHER_PREF", Context.MODE_PRIVATE)
            .edit().putString(preferenceName, value).apply()
    }

    override fun readString(preferenceName: String): String? {
        return context.getSharedPreferences("WEATHER_PREF", Context.MODE_PRIVATE)
            .getString(preferenceName, "")
    }

    override fun readBoolean(preferenceName: String): Boolean {
        return context.getSharedPreferences("WEATHER_PREF", Context.MODE_PRIVATE)
            .getBoolean(preferenceName, false)
    }

    override fun saveBoolean(preferenceName: String, value: Boolean) {
        context.getSharedPreferences("WEATHER_PREF", Context.MODE_PRIVATE)
            .edit().putBoolean(preferenceName, value).apply()
    }
}