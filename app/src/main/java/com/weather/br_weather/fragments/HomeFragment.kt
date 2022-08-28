package com.weather.br_weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.weather.br_weather.R
import com.weather.br_weather.adapters.WeatherPageAdapter
import com.weather.br_weather.model.City
import com.weather.br_weather.network.ResponseCode
import com.weather.br_weather.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchButton: ImageButton
    private lateinit var weatherPageAdapter: WeatherPageAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var emptyStateBackground: ConstraintLayout
    private lateinit var searchActionButton: Button
    private lateinit var sunIconImage: ImageView
    private var isEmpty = true

    @Inject
    lateinit var animationHelper: Animation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_home, container, false)
        val fm: FragmentManager = childFragmentManager
        val lifecycle: Lifecycle = viewLifecycleOwner.lifecycle
        weatherPageAdapter = WeatherPageAdapter(fm, lifecycle)
        viewPager = layout.findViewById(R.id.view_pager)
        searchButton = layout.findViewById(R.id.search_button)
        tabLayout = layout.findViewById(R.id.tab_layout)
        emptyStateBackground = layout.findViewById(R.id.empty_state_background)
        searchActionButton = layout.findViewById(R.id.search_action_button)
        sunIconImage = layout.findViewById(R.id.sun_image_icon)
        viewPager.adapter = weatherPageAdapter
        initObservers()
        initListeners()
        TabLayoutMediator(tabLayout, viewPager)
        { _, _ -> }.attach()
        viewModel.getSavedCities()


        return layout
    }

    private fun initObservers() {
        //Called when a city is selected in CitySearchFragment
        viewModel.selectCity.observe(viewLifecycleOwner) {
            when (it.responseCode) {
                ResponseCode.SUCCESS -> {
                    val city = it.data
                    city?.let { it1 -> weatherPageAdapter.addFragment(it1) }
                    hideEmptyState()
                }

            else -> {}
        }

        it.responseCode = ResponseCode.DEFAULT

    }

    //Called when city icon is deleted within WeatherPageFragment
    viewModel.removeCityResponse.observe(viewLifecycleOwner)
    {
        city ->
        weatherPageAdapter.removeCity(city)
        if (weatherPageAdapter.itemCount == 0) {
            isEmpty = true
            showEmptyState()

        }

    }
    //Initial call to get all of cities saved in memory
    viewModel.getCitiesResponse.observe(viewLifecycleOwner)
    {
        when (it.responseCode) {
            ResponseCode.SUCCESS -> {
                val cityList = it.data
                if (cityList != null && cityList.isNotEmpty()) {
                    showWeatherView(cityList)
                    hideEmptyState()
                }

            }

            else -> {}
        }
        it.responseCode = ResponseCode.DEFAULT

    }

}

private fun showEmptyState() {
    isEmpty = true
    sunIconImage.startAnimation(animationHelper)
    isEmpty = true
    emptyStateBackground.isVisible = true

}

private fun hideEmptyState() {
    isEmpty = true
    sunIconImage.clearAnimation()
    emptyStateBackground.isVisible = false

}

private fun showWeatherView(cityList: List<City>) {
    isEmpty = false
    weatherPageAdapter.initAdapter(cityList)
    viewPager.currentItem = 0
    emptyStateBackground.isVisible = false

}

private fun initListeners() {
    searchButton.setOnClickListener {
        val searchFragment =
            CitySearchFragment()
        searchFragment.show(
            childFragmentManager,
            searchFragment.tag
        )
    }

    //Jumps to viewpager index when a city already exsists
    weatherPageAdapter.setWeatherListener { index ->
        viewPager.currentItem = index
    }

    searchActionButton.setOnClickListener {
        val searchFragment =
            CitySearchFragment()
        searchFragment.show(
            childFragmentManager,
            searchFragment.tag
        )

    }


}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]


}

override fun onDestroyView() {
    super.onDestroyView()
    viewPager.adapter = null

}

}




