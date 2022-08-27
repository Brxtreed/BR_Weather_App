package com.weather.br_weather.Util

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.weather.br_weather.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AnimationModule {

    @Provides
    fun provideAnimation(@ApplicationContext appContext: Context): Animation {
        val animation = AnimationUtils.loadAnimation(appContext, R.anim.rotate)
        animation.fillAfter = true
        return animation
    }
}