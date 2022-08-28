package com.weather.br_weather.Util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.br_weather.dao.CityDao
import com.weather.br_weather.model.City

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CityDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao

}