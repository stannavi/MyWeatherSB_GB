package com.gb.myweathersb_gb.view.weatherlist

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
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.utils.SP_DB_NAME_IS_RUSSIAN
import com.gb.myweathersb_gb.utils.SP_KEY_IS_RUSSIAN
import com.gb.myweathersb_gb.view.details.DetailsFragment
import com.gb.myweathersb_gb.view.details.OnItemClick
import com.gb.myweathersb_gb.viewmodel.citieslist.CityListFragmentAppState
import com.gb.myweathersb_gb.viewmodel.citieslist.CitiesListViewModel
import com.google.android.material.snackbar.Snackbar

class CitiesListFragment : Fragment(), OnItemClick {
    companion object {
        fun newInstance() = CitiesListFragment()
    }

    var isRussian = true

    var _binding: FragmentCitiesListBinding? = null
    val binding: FragmentCitiesListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val viewModel: CitiesListViewModel by lazy {
        ViewModelProvider(this).get(CitiesListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) {t -> renderData(t)}

        binding.weatherListFragmentFAB.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getWeatherListForRussia()
                binding.weatherListFragmentFAB.apply {
                    setImageResource(R.drawable.ic_russia)
                }
            } else {
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
            val sp = requireActivity().getSharedPreferences(SP_DB_NAME_IS_RUSSIAN, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putBoolean(SP_KEY_IS_RUSSIAN, isRussian)
            editor.apply()
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(cityListFragmentAppState: CityListFragmentAppState) {
        when (cityListFragmentAppState) {
            is CityListFragmentAppState.Error -> {
                binding.showResult()
            }
            CityListFragmentAppState.Loading -> {
                binding.loading()
            }
            is CityListFragmentAppState.SuccessSingle -> {
                binding.showResult()
                val result = cityListFragmentAppState.weatherData
            }
            is CityListFragmentAppState.SuccessMulti -> {
                binding.showResult()
                binding.mainFragmentRecyclerView.adapter =
                    CitiesListAdapter(cityListFragmentAppState.weatherList, this)

            }
        }
    }

    fun FragmentCitiesListBinding.loading() {
        this.mainFragmentLoadingLayout.visibility = View.VISIBLE
        this.weatherListFragmentFAB.visibility = View.GONE
    }

    fun FragmentCitiesListBinding.showResult() {
        this.mainFragmentLoadingLayout.visibility = View.GONE
        this.weatherListFragmentFAB.visibility = View.VISIBLE
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }
}