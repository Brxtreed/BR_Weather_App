package com.weather.br_weather.viewModels

import androidx.lifecycle.*
import com.weather.br_weather.model.City
import com.weather.br_weather.network.ResponseCode
import com.weather.br_weather.network.ResponseHolder
import com.weather.br_weather.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: CityRepository
) : ViewModel() {

    var city: City? = null
    val selectCity = MutableLiveData<ResponseHolder<City>>()
    val removeCityResponse = MutableLiveData<City>()
    val getCitiesResponse = MutableLiveData<ResponseHolder<List<City>>>()

    //Gets the saved cities from database and posts response to live data
    fun getSavedCities() {
        viewModelScope.launch(Dispatchers.IO) {
            val cities = cityRepository.getCities()
            val responseHolder = ResponseHolder<List<City>>()
            responseHolder.responseCode = ResponseCode.SUCCESS
            responseHolder.data = cities
            getCitiesResponse.postValue(responseHolder)

        }
    }

    //Saves the city and posts the value to live data
    fun saveCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.addCity(city)
            val responseHolder = ResponseHolder<City>()
            responseHolder.responseCode = ResponseCode.SUCCESS
            responseHolder.data = city
            selectCity.postValue(responseHolder)
        }
    }

    //Removes the city and posts value to live data
    fun removeCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.deleteCity(city)
        }

        removeCityResponse.postValue(city)
    }


}
