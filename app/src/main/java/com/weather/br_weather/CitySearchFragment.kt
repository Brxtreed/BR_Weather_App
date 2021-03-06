package com.weather.br_weather

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.weather.br_weather.adapters.SearchAdapter
import com.weather.br_weather.network.ResponseCode
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

        cityInputSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchCities(v.text.toString())
                searchRecycler.isVisible = false
                progressBar.isVisible = true
                return@OnEditorActionListener true
            }
            false
        })

        searchAdapter.setCityListener {
            homeViewModel.saveCity(it)
            dismiss()
        }

    }

    private fun initObservers() {
        viewModel.searchCityResponse.observe(viewLifecycleOwner) {

            when (it.responseCode) {
                ResponseCode.SUCCESS -> {
                    it.data?.cities.let {
                        if (it != null) {
                            if(it.size > 0) {
                                searchAdapter.addCities(it)
                                searchRecycler.isVisible = true
                                noResultsText.isVisible = false

                            }
                            else {
                                searchRecycler.isVisible = false
                                noResultsText.isVisible = true

                            }
                            progressBar.isVisible = false
                        }

                    }
                }

                ResponseCode.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()


                }
                else -> {}
            }
            it.responseCode = ResponseCode.DEFAULT
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CitySearchViewModel::class.java]
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        val width = size.x
        val height = size.y
        display.getSize(size)
        window?.setLayout((width), (height))
        window?.setGravity(Gravity.CENTER)
        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#66000000")))}
            Configuration.UI_MODE_NIGHT_NO -> { dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D9FFFFFF")))}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> { dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D9FFFFFF")))}
        }

    }

    inner class Handler{

        fun dismiss(view: View) {
            dismiss()
        }

    }

}