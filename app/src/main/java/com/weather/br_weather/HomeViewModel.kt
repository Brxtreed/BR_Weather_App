package com.weather.br_weather

import androidx.lifecycle.*
import com.google.gson.Gson
import com.weather.br_weather.Util.LocalStorage
import com.weather.br_weather.Util.PreferenceConstants
import com.weather.br_weather.model.City
import com.weather.br_weather.network.requesters.GetCityDetailsRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCityDetailsRequester: GetCityDetailsRequester,
    private val localStorage: LocalStorage) : ViewModel() {

    var city: City? = null
    val getCityDetailsResponse = getCityDetailsRequester.liveResponseHolder
    val savedCityResponse = MutableLiveData<Boolean>()
    val selectCity = MutableLiveData<City>()

    fun getSavedCities(): City?{
        var savedCity: City
        val gson = Gson()

        try {
            savedCity = gson.fromJson(
                localStorage.readString(PreferenceConstants.SAVED_CITIES),
                City::class.java
            )
        }
        catch (e: Exception){
            return null

        }
        return savedCity
    }


    fun saveCity(city: City){
        val gson = Gson()
        var savedCity = city
        val savedCitiesJson = gson.toJson(savedCity)
        localStorage.saveString(PreferenceConstants.SAVED_CITIES, savedCitiesJson)
        selectCity.postValue(city)
    }

    fun getCityInfo(city: City){
        city.geonameid?.let { getCityDetailsRequester.startRequest(it) }


    }








}