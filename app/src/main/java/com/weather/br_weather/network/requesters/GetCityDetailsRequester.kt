package com.weather.br_weather.network.requesters

import com.weather.br_weather.model.CityDetail
import com.weather.br_weather.network.Requester
import com.weather.br_weather.network.services.WeatherService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class GetCityDetailsRequester @Inject constructor(private val retrofit: Retrofit) :
    Requester<CityDetail>() {
    override fun retry(call: Call<CityDetail>, response: Response<CityDetail>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun startRequest(cityId: Int) {
        retrofit.create(WeatherService::class.java)
            .getCiyData(cityId)
            .enqueue(this)
    }

}
