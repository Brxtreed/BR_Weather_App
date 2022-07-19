package com.weather.br_weather.network.services

import com.weather.br_weather.model.CitiesData
import com.weather.br_weather.model.CityDetail
import com.weather.br_weather.model.RadarImage
import retrofit2.Call
import retrofit2.http.*


interface WeatherService {
    @GET("/cities")
    fun searchCities(@Query("search") searchTerm: String): Call<CitiesData>

    @GET("cities/{cityId}")
    fun getCiyData(@Path("cityId") cityId: Int): Call<CityDetail>

    @GET("/cities/{cityId}/radar")
    fun getRadar(@Path("cityId") cityId: Int): Call<RadarImage>

}