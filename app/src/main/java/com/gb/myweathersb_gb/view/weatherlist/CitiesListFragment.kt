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
import com.gb.myweathersb_gb.databinding.FragmentWeatherListBinding
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.utils.SP_BD_NAME_IS_RUSSIAN
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

    var _binding: FragmentWeatherListBinding? = null
    val binding: FragmentWeatherListBinding
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
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<CityListFragmentAppState> {
            override fun onChanged(t: CityListFragmentAppState) {
                renderData(t)
            }
        })


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
            val sp = requireActivity().getSharedPreferences(SP_BD_NAME_IS_RUSSIAN, Context.MODE_PRIVATE)
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
                binding.root.HW2("????????????", Snackbar.LENGTH_SHORT, "?????????????????????? ?????? ??????") { _ ->
                    if (isRussian) {
                        viewModel.getWeatherListForRussia()
                    } else {
                        viewModel.getWeatherListForWorld()
                    }
                }
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
                    DetailsListAdapter(cityListFragmentAppState.weatherList, this)

            }
        }
    }

    fun View.HW(string: String, duration: Int) {
        Snackbar.make(this, string, duration).show()
    }

    fun View.HW2(string: String, duration: Int, actionString: String, block:(v:View)->Unit) {
        Snackbar.make(this, string, duration).setAction(actionString, block).show()
    }

    fun FragmentWeatherListBinding.loading() {
        this.mainFragmentLoadingLayout.visibility = View.VISIBLE
        this.weatherListFragmentFAB.visibility = View.GONE
    }

    fun FragmentWeatherListBinding.showResult() {
        this.mainFragmentLoadingLayout.visibility = View.GONE
        this.weatherListFragmentFAB.visibility = View.VISIBLE
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }
}