package com.weather.br_weather.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.weather.br_weather.R
import com.weather.br_weather.Util.dayOfWeek
import com.weather.br_weather.Util.farenheightTemp
import com.weather.br_weather.model.Day

class DailyTemperatureAdapter(context: Context, private var days: List<Day>, private var currentDay: Int) :
    ArrayAdapter<Day>(context, R.layout.cell_daily_weather, days) {

    private val inflater: LayoutInflater
    var dayClickLister: ((Day) -> Unit)? = null

    fun addDays(days: List<Day>) {

        val sortedDays = days.sortedWith(compareBy { it.dayOfTheWeek })
        for(day in sortedDays){
            if (day.dayOfTheWeek == currentDay){
                day.isSelected = true
                break
            }
        }
        if (sortedDays != null) {
            this.days = sortedDays
        }
        notifyDataSetChanged()

    }

    fun clearDays(){
        days = listOf()
        notifyDataSetChanged()
    }

    fun setDayListener(listener: (Day) -> Unit) {
        this.dayClickLister = listener

    }

    fun unselectDay(){
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
        var view = view

        if (view == null)
            view = inflater.inflate(R.layout.cell_daily_weather, parent, false)

        val dayNameText = view?.findViewById<TextView>(R.id.day_nam_text)
        val dayTempText = view?.findViewById<TextView>(R.id.day_temperature_text)
        val background = view?.findViewById<ConstraintLayout>(R.id.background)

        dayNameText?.text = day.dayOfWeek()
        dayTempText?.text = day.high.toString().farenheightTemp()

        if(day.isSelected || day.isCurrentDayOfWeek){
            dayNameText?.setTextColor(Color.WHITE)
            dayTempText?.setTextColor(Color.WHITE)
        }
        else {
            dayNameText?.setTextColor(Color.BLACK)
            dayTempText?.setTextColor(Color.BLACK)

        }

        background?.setOnClickListener {
            dayClickLister?.invoke(day)
            unselectDay()
            days[position].isSelected = true
            notifyDataSetChanged()
        }
        return view!!
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