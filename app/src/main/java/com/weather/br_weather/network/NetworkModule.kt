package com.weather.br_weather.network

import android.content.Context
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.google.gson.GsonBuilder
import com.weather.br_weather.network.requesters.CacheControlnterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    companion object {
        const val BASE_URL = "https://weather.exam.bottlerocketservices.com"
    }

    @Provides
    fun getRetrofit(client: OkHttpClient.Builder, exclude: Exclude): Retrofit {
        val gson = GsonBuilder().setLenient().addDeserializationExclusionStrategy(exclude)
            .addSerializationExclusionStrategy(exclude).create()
        val gsonFactory = GsonConverterFactory.create(gson)
            return Retrofit.Builder()
                .client(client.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(gsonFactory)
                .build()

    }

    @Provides
    fun provideOkHTTpClient(@ApplicationContext appContext: Context): OkHttpClient.Builder {

        val httpCacheDirectory = File(appContext.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB

        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(CacheControlnterceptor())
            .cache(cache)
    }

    @Provides
    fun provideNetworkMonitor(): NetworkRequest {

        return NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

    }
}