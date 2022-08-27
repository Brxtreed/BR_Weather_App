package com.weather.br_weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.weather.br_weather.R
import com.weather.br_weather.viewModels.RadarViewModel
import com.weather.br_weather.network.ResponseCode
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RadarFragment : Fragment() {
    private lateinit var viewModel: RadarViewModel
    private lateinit var radarView: ImageView
    private val args: RadarFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_radar, container, false)
        radarView = layout.findViewById(R.id.radar_view)
        val cityDetail = args.cityDetail
        cityDetail.city?.geonameid?.let { viewModel.getRadarImage(it) }
        initObservers()
        return layout
    }

    private fun initObservers() {
        viewModel.getRadarImageResponse.observe(viewLifecycleOwner) {

            when (it.responseCode) {
                ResponseCode.SUCCESS -> {
                    val radarImage = it.data?.imageURLs?.iOSImageURLs
                    val options: RequestOptions = RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform()

                    Glide.with(requireContext())
                        .load(radarImage)
                        .apply(options)
                        .into(radarView)

                }

                ResponseCode.ERROR -> {
                    radarView.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_baseline_broken_image_24
                        )
                    )
                }
                else -> {}
            }
            it.responseCode = ResponseCode.DEFAULT
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RadarViewModel::class.java]
    }

}