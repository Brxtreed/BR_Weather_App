package com.weather.br_weather.viewModels

import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weather.br_weather.Util.LocalStorage
import com.weather.br_weather.Util.PreferenceConstants
import com.weather.br_weather.model.City
import com.weather.br_weather.network.ResponseCode
import com.weather.br_weather.network.ResponseHolder
import com.weather.br_weather.network.requesters.GetCityDetailsRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localStorage: LocalStorage) : ViewModel() {

    var city: City? = null
    val selectCity = MutableLiveData<ResponseHolder<City>>()
    val removeCityResponse = MutableLiveData<City>()

    fun getSavedCities(): List<City>?{
        val cityListGsonType = object : TypeToken<List<City>>() {}.type
        var cityList: List<City>? = listOf()

        try {
            cityList = Gson().fromJson(
                localStorage.readString(PreferenceConstants.SAVED_CITIES),
                cityListGsonType
            )
        }
        catch (e: Exception){
            return cityList

        }
        return cityList
    }


    fun saveCity(city: City){
        val savedCities = getSavedCities()
        var editedSavedCities: ArrayList<City> = arrayListOf()
        if(savedCities != null){
            editedSavedCities = ArrayList(savedCities)
        }
        val editCity = editedSavedCities.find { it.geonameid == city.geonameid }
        if(editCity == null) {
            editedSavedCities.add(city)
            val gson = Gson()
            val savedCitiesJson = gson.toJson(editedSavedCities)
            localStorage.saveString(PreferenceConstants.SAVED_CITIES, savedCitiesJson)
        }
        val responseHolder = ResponseHolder<City>()
        responseHolder.responseCode = ResponseCode.SUCCESS
        responseHolder.data = city
        selectCity.postValue(responseHolder)
    }

    fun removeCity(city: City){
        val savedCities = getSavedCities()
        var editedSavedCities: ArrayList<City> = arrayListOf()
        if(savedCities != null){
            editedSavedCities = ArrayList(savedCities)
        }
        val index = editedSavedCities.indexOfFirst { it.geonameid == city.geonameid }

        if(index >= 0){
            editedSavedCities.removeAt(index)
            val gson = Gson()
            val savedCitiesJson = gson.toJson(editedSavedCities)
            localStorage.saveString(PreferenceConstants.SAVED_CITIES, savedCitiesJson)

        }

        removeCityResponse.postValue(city)
    }








}