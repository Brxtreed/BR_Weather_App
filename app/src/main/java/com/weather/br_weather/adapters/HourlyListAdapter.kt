package com.weather.br_weather.adapters

import com.weather.br_weather.model.HourlyWeather
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.br_weather.R
import com.weather.br_weather.Util.farenheightTemp
import com.weather.br_weather.Util.hour
import com.weather.br_weather.Util.humidity
import com.weather.br_weather.Util.rainChance
import com.weather.br_weather.model.WeatherIcon

class HourlyListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val HEADER = 0
    private val HOURLY_CELL = 1
    private var genericData: MutableList<Any> = mutableListOf()

    fun setHours(hourWeather: List<HourlyWeather>) {
        genericData.clear()
        val header = HeaderData()
        genericData.add(header)
        for (weather in hourWeather) {
            genericData.add(weather)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {

        when (genericData[position]) {

            is HeaderData -> {
                return HEADER
            }

            is HourlyWeather -> {
                return HOURLY_CELL
            }
        }
        return HEADER

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {

            HEADER -> {
                val headerHolder = holder as HeaderHolder
                configureHeaderHolder(headerHolder, position)
            }

            HOURLY_CELL -> {
                val headerHolder = holder as HourlyListHolder
                configureHourlyViewHolder(headerHolder, position)
            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // create a new view
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {


            HEADER -> {
                val v1 = inflater.inflate(R.layout.cell_hourly_header, parent, false)
                viewHolder = HeaderHolder(v1)
            }

            HOURLY_CELL -> {
                val v1 = inflater.inflate(R.layout.cell_hourly_weather, parent, false)
                viewHolder = HourlyListHolder(v1)
            }


            else -> {
                val v1 = inflater.inflate(R.layout.cell_hourly_header, parent, false)
                viewHolder = HeaderHolder(v1)
            }
        }
        return viewHolder
    }


    override fun getItemCount() = genericData.size

    private fun configureHourlyViewHolder(holder: HourlyListHolder, position: Int) {
        val hourWeather = genericData[position] as HourlyWeather
        holder.hourHumidityText.text = hourWeather.humidity()
        holder.hourRainText.text = hourWeather.rainChance()
        holder.hourTimeText.text = hourWeather.hour()
        holder.hourWindText.text = hourWeather.windSpeed.toString()
        holder.hourTempText.text = hourWeather.temperature.toString().farenheightTemp()

        when(hourWeather.weatherType){

            WeatherIcon.HEAVY_RAIN -> { holder.hourIconImage.setImageResource(R.drawable.icon_weather_active_ic_heavy_rain_active) }

            WeatherIcon.LIGHT_RAIN -> { holder.hourIconImage.setImageResource(R.drawable.icon_weather_active_ic_light_rain_active) }

            WeatherIcon.SNOW_SLEET -> { holder.hourIconImage.setImageResource(R.drawable.icon_weather_active_ic_snow_sleet_active) }

            WeatherIcon.CLOUDY -> { holder.hourIconImage.setImageResource(R.drawable.icon_weather_active_ic_cloudy_active) }

            WeatherIcon.SUNNY -> { holder.hourIconImage.setImageResource(R.drawable.icon_weather_active_ic_sunny_active) }

            WeatherIcon.PART_CLOUD -> { holder.hourIconImage.setImageResource(R.drawable.icon_weather_active_ic_partly_cloudy_active) }


        }

    }

    private fun configureHeaderHolder(holder: HeaderHolder, position: Int) {}


    class HourlyListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hourHumidityText: TextView = itemView.findViewById(R.id.hour_huimidity_text)
        val hourRainText: TextView = itemView.findViewById(R.id.hour_rain_text)
        val hourTimeText: TextView = itemView.findViewById(R.id.hour_time_text)
        val hourWindText: TextView = itemView.findViewById(R.id.hour_wind_text)
        val hourTempText: TextView = itemView.findViewById(R.id.hour_temp_text)
        val hourIconImage: ImageView = itemView.findViewById(R.id.hour_icon_image)

    }

    class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}