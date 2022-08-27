package com.weather.br_weather

import com.weather.br_weather.Util.hour
import com.weather.br_weather.Util.humidity
import com.weather.br_weather.Util.rainChance
import com.weather.br_weather.model.HourlyWeather
import org.junit.Assert
import org.junit.Test

class FormatterUnitTest {

    @Test
    fun testHourlyWeatherHourFormatter() {
        val hourlyWeather = HourlyWeather()
        hourlyWeather.hour = 0
        Assert.assertEquals("12AM", hourlyWeather.hour())
        hourlyWeather.hour = 1
        Assert.assertEquals("1AM", hourlyWeather.hour())
        hourlyWeather.hour = 2
        Assert.assertEquals("2AM", hourlyWeather.hour())
        hourlyWeather.hour = 3
        Assert.assertEquals("3AM", hourlyWeather.hour())
        hourlyWeather.hour = 4
        Assert.assertEquals("4AM", hourlyWeather.hour())
    }

    @Test
    fun testInvalidHourFormatter() {
        val hourlyWeather = HourlyWeather()
        hourlyWeather.hour = -213
        Assert.assertEquals("", hourlyWeather.hour())
    }

    @Test
    fun testRainChance() {
        val hourlyWeather = HourlyWeather()
        hourlyWeather.rainChance = .20
        Assert.assertEquals("20%", hourlyWeather.rainChance())
    }

    @Test
    fun testInvalidRainChance() {
        val hourlyWeather = HourlyWeather()
        hourlyWeather.rainChance = -.20
        Assert.assertEquals("20%", hourlyWeather.rainChance())
    }

    @Test
    fun testHumidityFormat() {
        val hourlyWeather = HourlyWeather()
        hourlyWeather.humidity = .20
        Assert.assertEquals("20%", hourlyWeather.humidity())
    }

//    @Test
//    fun testHourlyWeatherHourFormatter() {
//        val hourlyWeather = HourlyWeather()
//        hourlyWeather.hour = 0
//        Assert.assertEquals("12AM", hourlyWeather.hour())
//        hourlyWeather.hour = 1
//        Assert.assertEquals("1AM", hourlyWeather.hour())
//        hourlyWeather.hour = 2
//        Assert.assertEquals("2AM", hourlyWeather.hour())
//        hourlyWeather.hour = 3
//        Assert.assertEquals("3AM", hourlyWeather.hour())
//        hourlyWeather.hour = 4
//        Assert.assertEquals("4AM", hourlyWeather.hour())
//    }
}