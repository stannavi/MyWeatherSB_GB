package com.gb.myweathersb_gb.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb.myweathersb_gb.databinding.FragmentDetailsBinding
import com.gb.myweathersb_gb.domain.Weather


class DetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { arg ->
            arg.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)?.let { weather ->
                renderData(weather)
            }
        }
    }

    private fun renderData(weather: Weather) {
        binding?.apply {
            this.cityName
            cityName
        }
        val resAlso = binding?.also { newIt ->
            newIt.cityName.text = ""
            val resLet = binding?.let { bindingMy ->
                bindingMy.cityName.toString()
                bindingMy.cityCoordinates.toString()
            }
        }
        val resAlso2 = binding?.also { qwwqr ->
            qwwqr.cityName.text = ""
            val resLet = binding?.run {
                cityName.toString()
                cityCoordinates.toString()
            }
        }
        val resRun = binding?.run { cityName.toString() }

        with(binding) {
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
        }

    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "sgrrdfge"
        fun newInstance(weather: Weather) = DetailsFragment().also { fr ->
            fr.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }
            fr.arguments = Bundle().also { bundle ->
                bundle.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
                bundle.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }
        }
    }
}
