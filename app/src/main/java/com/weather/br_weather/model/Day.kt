package com.weather.br_weather.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Day {
    @SerializedName("dayOfTheWeek")
    @Expose
    var dayOfTheWeek: Int? = null

    @SerializedName("low")
    @Expose
    var low: Int? = null

    @SerializedName("high")
    @Expose
    var high: Int? = null

    @SerializedName("weatherType")
    @Expose
    var weatherType: String? = null

    @SerializedName("hourlyWeather")
    @Expose
    var hourlyWeather: List<HourlyWeather>? = null

    var isSelected = false

    var isCurrentDayOfWeek = false
}