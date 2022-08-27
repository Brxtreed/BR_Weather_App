package com.weather.br_weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.br_weather.R
import com.weather.br_weather.model.City


class SearchAdapter :
    RecyclerView.Adapter<SearchAdapter.SearchListHolder>() {

    private var cityList: List<City> = listOf()
    private var citySearchListener: ((City) -> Unit)? = null

    fun setCityListener(listener: (City) -> Unit) {
        this.citySearchListener = listener

    }

    fun addCities(cityList: List<City>){
        this.cityList = cityList
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_city_search, parent, false)
        return SearchListHolder(view)
    }


    override fun getItemCount() = cityList.size

    override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
        val city = cityList[position]
        holder.searchText.text = city.name

        holder.itemView.setOnClickListener { citySearchListener?.invoke(city) }
    }


    class SearchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val searchText: TextView = itemView.findViewById(R.id.search_text)

    }


}
