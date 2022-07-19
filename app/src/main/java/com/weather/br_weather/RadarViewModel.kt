package com.weather.br_weather

import androidx.lifecycle.ViewModel
import com.weather.br_weather.network.requesters.GetRadarImageRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RadarViewModel @Inject constructor(
    private val getRadarImageRequester: GetRadarImageRequester
) : ViewModel() {
    val getRadarImageResponse = getRadarImageRequester.liveResponseHolder

    fun getRadarImage(cityId: Int){
        getRadarImageRequester.startRequest(cityId)
    }
}