package com.weather.br_weather.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class City() : Parcelable {
    @SerializedName("modification date")
    @Expose
    var modificationDate: String? = null

    @SerializedName("admin2 code")
    @Expose
    var admin2Code: Int? = null

    @SerializedName("country code")
    @Expose
    var countryCode: String? = null

    @SerializedName("population")
    @Expose
    var population: Int? = null

    @SerializedName("asciiname")
    @Expose
    var asciiname: String? = null

    @PrimaryKey
    @SerializedName("geonameid")
    @Expose
    var geonameid: Int? = null

    @SerializedName("dem")
    @Expose
    var dem: Int? = null

    @SerializedName("feature class")
    @Expose
    var featureClass: String? = null

    @SerializedName("admin4 code")
    @Expose
    var admin4Code: String? = null

    @SerializedName("cc2")
    @Expose
    var cc2: String? = null


    @Embedded
    @SerializedName("imageURLs")
    @Expose
    var imageURLs: ImageUrls? = null

    @SerializedName("admin3 code")
    @Expose
    var admin3Code: Int? = null

    @SerializedName("timezone")
    @Expose
    var timezone: String? = null

    @SerializedName("feature code")
    @Expose
    var featureCode: String? = null

    @SerializedName("elevation")
    @Expose
    var elevation: Int? = null

    @SerializedName("name")
    @Expose
    var name: String = ""

    @SerializedName("alternatenames")
    @Expose
    var alternatenames: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null

    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null

    @SerializedName("admin1 code")
    @Expose
    var admin1Code: String? = null

    constructor(parcel: Parcel) : this() {
        modificationDate = parcel.readString()
        admin2Code = parcel.readValue(Int::class.java.classLoader) as? Int
        countryCode = parcel.readString()
        population = parcel.readValue(Int::class.java.classLoader) as? Int
        asciiname = parcel.readString()
        geonameid = parcel.readValue(Int::class.java.classLoader) as? Int
        dem = parcel.readValue(Int::class.java.classLoader) as? Int
        featureClass = parcel.readString()
        admin4Code = parcel.readString()
        cc2 = parcel.readString()
        admin3Code = parcel.readValue(Int::class.java.classLoader) as? Int
        timezone = parcel.readString()
        featureCode = parcel.readString()
        elevation = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString().toString()
        alternatenames = parcel.readString()
        latitude = parcel.readValue(Double::class.java.classLoader) as? Double
        longitude = parcel.readValue(Double::class.java.classLoader) as? Double
        admin1Code = parcel.readString()
    }

    override fun toString(): String {
        return name
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(modificationDate)
        parcel.writeValue(admin2Code)
        parcel.writeString(countryCode)
        parcel.writeValue(population)
        parcel.writeString(asciiname)
        parcel.writeValue(geonameid)
        parcel.writeValue(dem)
        parcel.writeString(featureClass)
        parcel.writeString(admin4Code)
        parcel.writeString(cc2)
        parcel.writeValue(admin3Code)
        parcel.writeString(timezone)
        parcel.writeString(featureCode)
        parcel.writeValue(elevation)
        parcel.writeString(name)
        parcel.writeString(alternatenames)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeString(admin1Code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }

}