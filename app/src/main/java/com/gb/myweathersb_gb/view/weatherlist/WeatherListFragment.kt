package com.gb.myweathersb_gb.view.weatherlist

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
import com.gb.myweathersb_gb.view.details.DetailsFragment
import com.gb.myweathersb_gb.view.details.OnItemClick
import com.gb.myweathersb_gb.viewmodel.AppState
import com.gb.myweathersb_gb.viewmodel.WeatherListViewModel
import com.google.android.material.snackbar.Snackbar
import java.time.Duration

class WeatherListFragment : Fragment(), OnItemClick {
    companion object {
        fun newInstance() = WeatherListFragment()
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

    private val viewModel: WeatherListViewModel by lazy {
        ViewModelProvider(this).get(WeatherListViewModel::class.java)
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

        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
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
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.showResult()
                binding.root.HW2("Ошибка", Snackbar.LENGTH_SHORT, "Попробовать ещё раз") { _ ->
                    if (isRussian) {
                        viewModel.getWeatherListForRussia()
                    } else {
                        viewModel.getWeatherListForWorld()
                    }
                }
            }
            AppState.Loading -> {
                binding.loading()
            }
            is AppState.SuccessSingle -> {
                binding.showResult()
                val result = appState.weatherData
            }
            is AppState.SuccessMulti -> {
                binding.showResult()
                binding.mainFragmentRecyclerView.adapter =
                    WeatherListAdapter(appState.weatherList, this)

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