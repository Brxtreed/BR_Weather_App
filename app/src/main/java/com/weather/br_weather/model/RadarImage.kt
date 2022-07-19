package com.weather.br_weather.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class RadarImage {
    @SerializedName("imageURLs")
    @Expose
    var imageURLs: ImageUrls? = null
}
