package com.gb.myweathersb_gb.view.details.room

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gb.myweathersb_gb.R
import com.gb.myweathersb_gb.databinding.FragmentCitiesListBinding
import com.gb.myweathersb_gb.databinding.FragmentWeatherHistoryListBinding
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.utils.SP_DB_NAME_IS_RUSSIAN
import com.gb.myweathersb_gb.utils.SP_KEY_IS_RUSSIAN
import com.gb.myweathersb_gb.view.details.DetailsFragment
import com.gb.myweathersb_gb.view.details.OnItemClick
import com.gb.myweathersb_gb.view.weatherlist.DetailsListAdapter
import com.gb.myweathersb_gb.viewmodel.citieslist.CityListFragmentAppState
import com.gb.myweathersb_gb.viewmodel.citieslist.CitiesListViewModel
import com.gb.myweathersb_gb.viewmodel.weatherhistorylist.WeatherHistoryListFragmentAppState
import com.gb.myweathersb_gb.viewmodel.weatherhistorylist.WeatherHistoryListViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherHistoryListFragment : Fragment(), OnItemClick {
    companion object {
        fun newInstance() = WeatherHistoryListFragment()
    }

    var isRussian = true

    var _binding: FragmentWeatherHistoryListBinding? = null
    val binding: FragmentWeatherHistoryListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    lateinit var viewModel: WeatherHistoryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherHistoryListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherHistoryListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) {t -> renderData(t)}
        viewModel.getAllHistory()
    }

    private fun renderData(weatherHistoryListFragmentAppState: WeatherHistoryListFragmentAppState) {
        when (weatherHistoryListFragmentAppState) {

            is WeatherHistoryListFragmentAppState.Error -> {
            }
            WeatherHistoryListFragmentAppState.Loading -> {
            }
            is WeatherHistoryListFragmentAppState.SuccessMulti -> {
                binding.historyFragmentRecyclerView.adapter =
                    WeatherHistoryListAdapter(WeatherHistoryListFragmentAppState.weatherList, this)

            }
        }
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }
}