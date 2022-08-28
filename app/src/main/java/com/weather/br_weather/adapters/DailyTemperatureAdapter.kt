package com.weather.br_weather.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.weather.br_weather.R
import com.weather.br_weather.Util.dayOfWeek
import com.weather.br_weather.Util.farenheightTemp
import com.weather.br_weather.model.Day
import com.weather.br_weather.model.WeatherIcon

class DailyTemperatureAdapter(context: Context, private var days: List<Day>, private var currentDay: Int) :
    ArrayAdapter<Day>(context, R.layout.cell_daily_weather, days) {

    private val inflater: LayoutInflater
    private var dayClickLister: ((Day) -> Unit)? = null

    fun addDays(days: List<Day>) {

        val sortedDays = days.sortedWith(compareBy { it.dayOfTheWeek })
        for(day in sortedDays){
            if (day.dayOfTheWeek == currentDay){
                day.isSelected = true
                break
            }
        }
        this.days = sortedDays
        notifyDataSetChanged()

    }

    fun clearDays(){
        days = listOf()
        notifyDataSetChanged()
    }

    fun setDayListener(listener: (Day) -> Unit) {
        this.dayClickLister = listener

    }

    private fun unselectDay(){
        for(day in days){
            day.isSelected = false
            day.isCurrentDayOfWeek = false
        }
    }


    init {
        inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val day = days[position]
        var itemView = view

        if (itemView == null)
            itemView = inflater.inflate(R.layout.cell_daily_weather, parent, false)

        val dayNameText = itemView?.findViewById<TextView>(R.id.day_nam_text)
        val dayTempText = itemView?.findViewById<TextView>(R.id.day_temperature_text)
        val background = itemView?.findViewById<ConstraintLayout>(R.id.background)
        val iconImage  = itemView?.findViewById<ImageView>(R.id.weather_icon_image)

        dayNameText?.text = day.dayOfWeek()
        dayTempText?.text = day.high.toString().farenheightTemp()

        if(day.isSelected || day.isCurrentDayOfWeek){
            dayNameText?.setTextColor(Color.WHITE)
            dayTempText?.setTextColor(Color.WHITE)
            iconImage?.setColorFilter(Color.WHITE)
        }
        else {
            dayNameText?.setTextColor(Color.BLACK)
            dayTempText?.setTextColor(Color.BLACK)
            iconImage?.setColorFilter(Color.BLACK)

        }

        when(day.weatherType){

            WeatherIcon.HEAVY_RAIN -> { iconImage?.setImageResource(R.drawable.icon_weather_active_ic_heavy_rain_active) }

            WeatherIcon.LIGHT_RAIN -> { iconImage?.setImageResource(R.drawable.icon_weather_active_ic_light_rain_active) }

            WeatherIcon.SNOW_SLEET -> { iconImage?.setImageResource(R.drawable.icon_weather_active_ic_snow_sleet_active) }

            WeatherIcon.CLOUDY -> { iconImage?.setImageResource(R.drawable.icon_weather_active_ic_cloudy_active) }

            WeatherIcon.SUNNY -> { iconImage?.setImageResource(R.drawable.icon_weather_active_ic_sunny_active) }

            WeatherIcon.PART_CLOUD -> { iconImage?.setImageResource(R.drawable.icon_weather_active_ic_partly_cloudy_active) }


        }

        background?.setOnClickListener {
            dayClickLister?.invoke(day)
            unselectDay()
            days[position].isSelected = true
            notifyDataSetChanged()
        }
        return itemView!!
    }

    override fun getCount(): Int {
        return days.size
    }
    override fun getItem(position: Int): Day {
        return days[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}