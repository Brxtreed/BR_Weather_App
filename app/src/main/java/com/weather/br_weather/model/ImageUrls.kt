package com.weather.br_weather.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ImageUrls: Serializable {

    @SerializedName("iOSImageURLs")
    @Expose
    var iOSImageURLs: IOSImageURLs? = null


}