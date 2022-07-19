package com.weather.br_weather.network.requesters

import com.weather.br_weather.model.CitiesData
import com.weather.br_weather.network.Requester
import com.weather.br_weather.network.services.WeatherService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class SearchCityRequester @Inject constructor(private val retrofit: Retrofit) :
    Requester<CitiesData>() {
    override fun retry(call: Call<CitiesData>, response: Response<CitiesData>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun startRequest(searchTerm: String) {
        retrofit.create(WeatherService::class.java)
            .searchCities(searchTerm)
            .enqueue(this)
    }

}
