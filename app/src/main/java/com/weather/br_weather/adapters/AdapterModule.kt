package com.weather.br_weather.adapters

import android.content.Context
import com.weather.br_weather.Util.getDayOfWeek
import com.weather.br_weather.model.Day
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(FragmentComponent::class)
class AdapterModule {
    @Provides
    fun provideCalendarAdapter(@ActivityContext context: Context): DailyTemperatureAdapter {
        val days: List<Day> = listOf()
        return DailyTemperatureAdapter(context, days, getDayOfWeek())

    }
}