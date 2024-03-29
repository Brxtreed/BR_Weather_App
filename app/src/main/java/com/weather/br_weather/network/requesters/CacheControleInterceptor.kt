package com.weather.br_weather.network.requesters

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit


class CacheControleInterceptor : Interceptor {
    val cacheMaxAge = 15
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(cacheMaxAge, TimeUnit.MINUTES) // 15 minutes cache
            .build()
        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}

