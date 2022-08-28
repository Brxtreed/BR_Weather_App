package com.weather.br_weather.fragments

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.weather.br_weather.R
import com.weather.br_weather.adapters.SearchAdapter
import com.weather.br_weather.network.ResponseCode
import com.weather.br_weather.viewModels.CitySearchViewModel
import com.weather.br_weather.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitySearchFragment : DialogFragment() {

    private lateinit var viewModel: CitySearchViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cityInputSearch: EditText
    private lateinit var dismissImage: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var searchRecycler: RecyclerView
    private lateinit var noResultsText: TextView
    private val searchAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_city_search, container, false)
        cityInputSearch = layout.findViewById(R.id.searchView)
        dismissImage = layout.findViewById(R.id.dismiss_image)
        progressBar = layout.findViewById(R.id.progressBar)
        searchRecycler = layout.findViewById(R.id.search_recycle_view)
        noResultsText = layout.findViewById(R.id.no_results_text)
        searchRecycler.adapter = searchAdapter

        initObservers()
        initListeners()



        return layout
    }

    private fun initListeners() {
        dismissImage.setOnClickListener {
            dismiss()
        }

        cityInputSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchCities(v.text.toString())
                searchRecycler.isVisible = false
                progressBar.isVisible = true
                return@OnEditorActionListener true
            }
            false
        })

        //On city selected pops view and adds city to HomeFragment
        searchAdapter.setCityListener {
            homeViewModel.saveCity(it)
            dismiss()
        }

    }

    private fun initObservers() {
        viewModel.searchCityResponse.observe(viewLifecycleOwner) {

            when (it.responseCode) {
                ResponseCode.SUCCESS -> {
                    it.data?.cities.let { cityList ->
                        if (cityList != null) {
                            if (cityList.isNotEmpty()) {
                                searchAdapter.addCities(cityList)
                                searchRecycler.isVisible = true
                                noResultsText.isVisible = false

                            } else {
                                searchRecycler.isVisible = false
                                noResultsText.isVisible = true

                            }
                            progressBar.isVisible = false
                        }

                    }
                }

                ResponseCode.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    searchRecycler.isVisible = false
                    noResultsText.isVisible = true
                    progressBar.isVisible = false


                }
                else -> {}
            }
            it.responseCode = ResponseCode.DEFAULT
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CitySearchViewModel::class.java]
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        setDialogWindowSize()
        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#66000000")))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D9FFFFFF")))
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D9FFFFFF")))
            }
        }

    }

    @Suppress("DEPRECATION")
    fun setDialogWindowSize() {
        val window = dialog?.window
        val wm = requireActivity().windowManager
        val width: Int
        val height: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val windowInsets: WindowInsets = windowMetrics.windowInsets

            val insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout()
            )
            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom

            val b = windowMetrics.bounds
            width = b.width() - insetsWidth
            height = b.height() - insetsHeight
        } else {
            val size = Point()
            val display = wm.defaultDisplay // deprecated in API 30
            display?.getSize(size) // deprecated in API 30
            width = size.x
            height = size.y
        }
        window?.setLayout((width), (height))
        window?.setGravity(Gravity.CENTER)
    }


}