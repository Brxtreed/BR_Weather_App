package com.weather.br_weather.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.weather.br_weather.fragments.WeatherPageFragment
import com.weather.br_weather.model.City

class WeatherPageAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    private val weatherFragments: ArrayList<WeatherPageFragment> = ArrayList()
    private var weatherPageListener: ((Int) -> Unit)? = null

    fun setWeatherListener(listener: (Int) -> Unit) {
        this.weatherPageListener = listener

    }

    fun addFragment(city: City){
        if(findCity(city) == null){
            val weatherPageFragment = WeatherPageFragment()
            val bundle = Bundle()
            bundle.putParcelable("city", city)
            weatherPageFragment.arguments = bundle
            weatherFragments.add(weatherPageFragment)
            weatherPageListener?.invoke(weatherFragments.size)
            notifyDataSetChanged()
        }


    }

    fun initAdapter(cityList: List<City>){
        for (city in cityList) {
            val weatherPageFragment = WeatherPageFragment()
            val bundle = Bundle()
            bundle.putParcelable("city", city)
            weatherPageFragment.arguments = bundle
            weatherFragments.add(weatherPageFragment)

        }
        notifyDataSetChanged()

    }

    fun removeCity(city: City){
        val index = weatherFragments.indexOfFirst { it.city?.geonameid == city.geonameid }
        if(index >= 0){
            weatherFragments.removeAt(index)
        }
        notifyItemRemoved(index)

    }

    override fun getItemCount(): Int {
        return weatherFragments.size //Number of fragments displayed
    }


    override fun createFragment(position: Int): Fragment {
        return weatherFragments[position]
    }

    private fun findCity(city: City): WeatherPageFragment?{
        val citySearch = weatherFragments.find { it.city?.geonameid == city.geonameid }
        if(citySearch != null){
            val index = weatherFragments.indexOf(citySearch)
            weatherPageListener?.invoke(index)
        }

        return citySearch
    }

    override fun getItemId(position: Int): Long {
        return weatherFragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return true
    }
}