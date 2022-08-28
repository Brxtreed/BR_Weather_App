package com.weather.br_weather.dao

import androidx.room.*
import com.weather.br_weather.model.City

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun loadAllCities(): List<City>

    @Delete
    fun deleteCity(city: City)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)
}