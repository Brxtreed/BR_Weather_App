package com.weather.br_weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.weather.br_weather.R
import com.weather.br_weather.Util.farenheightTemp
import com.weather.br_weather.Util.getCurrentTime
import com.weather.br_weather.Util.getDayOfWeek
import com.weather.br_weather.adapters.DailyTemperatureAdapter
import com.weather.br_weather.adapters.HourlyListAdapter
import com.weather.br_weather.model.City
import com.weather.br_weather.network.ResponseCode
import com.weather.br_weather.viewModels.HomeViewModel
import com.weather.br_weather.viewModels.WeatherPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherPageFragment : Fragment() {
    private lateinit var viewModel: WeatherPageViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cityImage: ImageView
    private lateinit var cityNameText: TextView
    private lateinit var dateTimeText: TextView
    private lateinit var temperatureText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewRadarButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var weatherView: ConstraintLayout
    private lateinit var dailyGridView: GridView
    private lateinit var deleteButton: ImageButton
    var city: City? = null

    @Inject
    lateinit var dailyGridAdapter: DailyTemperatureAdapter
    private val hourlyListAdapter = HourlyListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_weather_page, container, false)
        city = arguments?.getParcelable("city") as City?
        cityImage = layout.findViewById(R.id.city_image)
        cityNameText = layout.findViewById(R.id.city_name_text)
        dateTimeText = layout.findViewById(R.id.date_time_text)
        temperatureText = layout.findViewById(R.id.temperature_text)
        recyclerView = layout.findViewById(R.id.recycler_vew)
        viewRadarButton = layout.findViewById(R.id.view_radar_button)
        progressBar = layout.findViewById(R.id.progressBar)
        weatherView = layout.findViewById(R.id.weather_view)
        dailyGridView = layout.findViewById(R.id.gridview)
        deleteButton = layout.findViewById(R.id.delete_button)
        progressBar.isVisible = true
        viewRadarButton.isVisible = false
        initViews()
        initListeners()
        initObservers()


        return layout
    }

    private fun initObservers() {
        //Retrofit response holding the cityDetail information
        viewModel.getCityDetailsResponse.observe(viewLifecycleOwner) {
            initListeners()

            when (it.responseCode) {
                ResponseCode.SUCCESS -> {
                    if (!it.isCacheResult) {
                        Toast.makeText(
                            activity,
                            "Request was not a cached response",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    it.data?.let { city ->
                        viewRadarButton.isVisible = true
                        progressBar.isVisible = false
                        city.weather?.days?.let { days -> dailyGridAdapter.addDays(days) }
                        dailyGridView.adapter = dailyGridAdapter
                        city.weather?.days?.get(1)!!.high.toString().farenheightTemp()
                        if (city.weather?.days != null) {
                            val dailyWeather =
                                city.weather?.days!!.find { day -> day.dayOfTheWeek == getDayOfWeek() }
                            temperatureText.text = dailyWeather?.high.toString().farenheightTemp()
                            dailyWeather?.hourlyWeather?.let { it1 -> hourlyListAdapter.setHours(it1) }

                        }
                        recyclerView.adapter = hourlyListAdapter
                    }
                }

                ResponseCode.ERROR -> {
                    progressBar.isVisible = false
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()


                }
                else -> {}
            }
            it.responseCode = ResponseCode.DEFAULT
        }
    }

    private fun initViews() {
        dateTimeText.text = getCurrentTime()
        if (city != null) {
            cityNameText.text = city?.name

            viewModel.getCityInfo(city!!)

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()

            Glide.with(requireContext())
                .load(city?.imageURLs?.iOSImageURLs?.imageURL)
                .apply(options)
                .into(cityImage)
            dailyGridAdapter.clearDays()
            progressBar.isVisible = true


        } else {

            cityImage.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_broken_image_24
                )
            )
            cityNameText.text = getString(R.string.no_city_found_error_string)
            progressBar.isVisible = false
        }
    }

    private fun initListeners() {

        viewRadarButton.setOnClickListener {
            val action =
                viewModel.getCityDetailsResponse.value?.data?.let { it1 ->
                    HomeFragmentDirections.navigationRadar(
                        it1
                    )
                }
            if (action != null) {
                view?.findNavController()?.navigate(action)
            }
        }

        deleteButton.setOnClickListener {
            city?.let { it1 -> homeViewModel.removeCity(it1) }
        }

        //Listener attatched to day views if selected, changes hourly list
        dailyGridAdapter.setDayListener {
            it.hourlyWeather?.let { hourlyWeather ->
                hourlyListAdapter.setHours(
                    hourlyWeather
                )
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherPageViewModel::class.java]
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]


    }


}




