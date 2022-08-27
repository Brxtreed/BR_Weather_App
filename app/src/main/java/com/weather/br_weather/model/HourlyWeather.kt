package com.weather.br_weather.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class HourlyWeather {
    @SerializedName("windSpeed")
    @Expose
    var windSpeed: Double? = 0.0

    @SerializedName("temperature")
    @Expose
    var temperature: Int? = null

    @SerializedName("weatherType")
    @Expose
    var weatherType: String? = null

    @SerializedName("humidity")
    @Expose
    var humidity: Double? = null

    @SerializedName("hour")
    @Expose
    var hour: Int? = null

    @SerializedName("rainChance")
    @Expose
    var rainChance: Double? = null

}
