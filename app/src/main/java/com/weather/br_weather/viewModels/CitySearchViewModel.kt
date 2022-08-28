package com.weather.br_weather.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.weather.br_weather.Util.CityDatabase
import com.weather.br_weather.network.requesters.SearchCityRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val searchCityRequester: SearchCityRequester,
    private val database: CityDatabase
) : ViewModel() {
    val searchCityResponse = searchCityRequester.liveResponseHolder

    init {
        viewModelScope.launch {
            searchCityResponse.asFlow().collect {

            }
        }


    }

    fun searchCities(searchTerm: String){
        searchCityRequester.startRequest(searchTerm)
    }

}