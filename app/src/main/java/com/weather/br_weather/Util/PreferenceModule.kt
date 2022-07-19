package com.weather.br_weather.Util

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): LocalStorage {
        return SharedPrefStorage(appContext)
    }
}