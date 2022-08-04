package com.gb.myweathersb_gb.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.gb.myweathersb_gb.BuildConfig
import com.gb.myweathersb_gb.R
import com.gb.myweathersb_gb.databinding.FragmentDetailsBinding
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import com.gb.myweathersb_gb.utils.*
import com.gb.myweathersb_gb.viewmodel.details.DetailsFragmentAppState
import com.gb.myweathersb_gb.viewmodel.details.DetailsViewModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    val viewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
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

        val weather = arguments?.let { arg ->
            arg.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)
        }

        weather?.let { weatherLocal ->
            viewModel.getWeather(weatherLocal)
            viewModel.getLiveData().observe(viewLifecycleOwner) {
                renderData(it)
            }
            viewModel.getWeather(it.city)
        }
    }

    private fun renderData(detailsFragmentAppState: DetailsFragmentAppState) {

        when (detailsFragmentAppState) {
            is DetailsFragmentAppState.Error -> {}
            DetailsFragmentAppState.Loading -> {}
            is DetailsFragmentAppState.Success -> {
                with(binding) {
                    val weather = detailsFragmentAppState.weatherData
                    cityName.text = weather.city.name
                    temperatureValue.text = weather.temperature.toString()
                    feelsLikeValue.text = weather.feelsLike.toString()
                    cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"


                    icon.loadUrl("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
                }
            }
        }
    }

    fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry{add(SvgDecoder(this@loadUrl.context))}
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
