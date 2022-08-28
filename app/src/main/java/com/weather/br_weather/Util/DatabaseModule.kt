package com.weather.br_weather.Util

import android.content.Context
import androidx.room.Room
import com.weather.br_weather.dao.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): CityDatabase {
        return Room.databaseBuilder(
            appContext,
            CityDatabase::class.java, "city-database"
        ).build()
    }

    @Provides
    fun provideCityDao(cityDatebase: CityDatabase): CityDao {
        return cityDatebase.cityDao()
    }
}