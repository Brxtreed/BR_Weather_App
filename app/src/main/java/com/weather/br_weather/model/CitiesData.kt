package com.weather.br_weather.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CitiesData {
    @SerializedName("totalCitiesFound")
    @Expose
    var totalCitiesFound: Int? = null

    @SerializedName("startIndex")
    @Expose
    var startIndex: Int? = null

    @SerializedName("cities")
    @Expose
    var cities: List<City>? = null


}
