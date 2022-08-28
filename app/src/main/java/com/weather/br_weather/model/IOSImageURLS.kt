package com.weather.br_weather.model

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class IOSImageURLs {
    @SerializedName("imageURL")
    @Expose
    var imageURL = ""

}

