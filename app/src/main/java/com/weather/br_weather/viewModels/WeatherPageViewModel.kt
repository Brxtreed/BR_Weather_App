package com.weather.br_weather.viewModels

import androidx.lifecycle.*
import com.weather.br_weather.model.City
import com.weather.br_weather.network.requesters.GetCityDetailsRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WeatherPageViewModel @Inject constructor(
    private val getCityDetailsRequester: GetCityDetailsRequester) : ViewModel() {

    var city: City? = null
    val getCityDetailsResponse = getCityDetailsRequester.liveResponseHolder

    fun getCityInfo(city: City){
        city.geonameid?.let { getCityDetailsRequester.startRequest(it) }
    }










}