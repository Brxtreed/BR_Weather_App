package com.weather.br_weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.weather.br_weather.model.City
import com.weather.br_weather.network.requesters.SearchCityRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val searchCityRequester: SearchCityRequester
) : ViewModel() {
    val searchCityResponse = searchCityRequester.liveResponseHolder
    private val filteredCityList = MutableLiveData<List<City>>()


    init {
        viewModelScope.launch {
            searchCityResponse.asFlow().collect {

            }
        }


    }

    fun searchCities(searchTerm: String){
        searchCityRequester.startRequest(searchTerm)
    }

    fun filterCities(name: String){
        val cities = searchCityResponse.value?.data?.cities?.filter { it.name.contains(name) }
        filteredCityList.value = cities

    }



}