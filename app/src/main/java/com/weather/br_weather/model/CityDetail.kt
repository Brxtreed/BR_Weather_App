package com.weather.br_weather.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CityDetail: Serializable {
    @SerializedName("city")
    @Expose
    var city: City? = null
    @SerializedName("weather")
    @Expose
    var weather: Weather? = null
    @SerializedName("hourlyWeather")
    @Expose
    var hourlyWeather: Weather? = null

}
