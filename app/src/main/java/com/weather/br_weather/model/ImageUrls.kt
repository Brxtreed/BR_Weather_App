package com.weather.br_weather.model

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class ImageUrls: Serializable {
    @Embedded
    @SerializedName("iOSImageURLs")
    @Expose
    var iOSImageURLs: IOSImageURLs? = null


}