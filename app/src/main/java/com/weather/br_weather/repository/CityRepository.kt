package com.weather.br_weather.repository

import com.weather.br_weather.dao.CityDao
import com.weather.br_weather.model.City
import javax.inject.Inject

class CityRepository@Inject constructor(private var cityDao: CityDao): CityRepositoryInterface {
    override suspend fun getCities(): List<City> = cityDao.loadAllCities()

    override suspend fun addCity(city: City) = cityDao.insertCity(city)

    override suspend fun deleteCity(city: City) = cityDao.deleteCity(city)
}