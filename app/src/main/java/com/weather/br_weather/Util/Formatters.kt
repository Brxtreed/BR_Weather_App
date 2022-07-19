package com.weather.br_weather.Util

import HourlyWeather.HourlyWeather
import com.weather.br_weather.model.Day
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate

fun String.farenheightTemp(): String {

    return "${this}\u00B0"

}

fun HourlyWeather.hour(): String {

    when(hour){
        0-> return "12AM"
        1-> return "1AM"
        2-> return "2AM"
        3-> return "3AM"
        4-> return "4AM"
        5-> return "5AM"
        6-> return "6AM"
        7-> return "8AM"
        8-> return "9AM"
        9-> return "10AM"
        10-> return "11AM"
        11-> return "12PM"
        12-> return "1PM"
        13-> return "2PM"
        14-> return "3PM"
        15-> return "4PM"
        16-> return "5PM"
        17-> return "6PM"
        18-> return "7PM"
        19-> return "8PM"
        20-> return "9PM"
        21-> return "10PM"
        22-> return "11PM"
        23-> return "12PM"

    }
    return ""

}

fun Day.dayOfWeek(): String {

    when(dayOfTheWeek){
        0-> return "Mon"
        1-> return "Tue"
        2-> return "Wed"
        3-> return "Thu"
        4-> return "Fri"
        5-> return "Sat"
        6-> return "Sun"
    }
    return ""

}

fun HourlyWeather.rainChance(): String {

    return "${rainChance?.times(100)?.toInt()}%"
    return ""


}

fun HourlyWeather.humidity(): String {

    return "${humidity?.times(100)?.toInt()}%"
    return ""


}

fun getCurrentTime(): String{

    return DateTime.now().withZone(DateTimeZone.getDefault()).toString("E M/dd/yy")

}

fun getDayOfWeek(): Int {
    var date = LocalDate()
    return date.dayOfWeek-1
}
