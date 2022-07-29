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
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gb.myweathersb_gb.BuildConfig
import com.gb.myweathersb_gb.databinding.FragmentDetailsBinding
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import com.gb.myweathersb_gb.utils.*
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("@@@", "onReceive ${binding.root}")
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(BUNDLE_WEATHER_DTO_KEY)?.let { weatherDTO ->
                    bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
                }
            }
        }
    }

    lateinit var weatherLocal: Weather

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
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
            this.weatherLocal = weatherLocal
            WeatherLoader.requestFirstVariant(
                weatherLocal.city.lat,
                weatherLocal.city.lon,
                object : OnReponse {
                    override fun onResponse(weather: WeatherDTO) {

                    }
                }
            )

            /*LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                receiver,
                IntentFilter(WAVE)
            )

            requireActivity().startService(Intent(requireContext(), DetailServiceIntent::class.java).apply {
                putExtra(BUNDLE_CITY_KEY, weatherLocal.city)
            })*/

            val client = OkHttpClient()
            val builder = Request.Builder()
            builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            builder.url("https://api.weather.yandex.ru/v2/informers?lat=${weatherLocal.city.lat}&lon=${weatherLocal.city.lon}")
            val request: Request = builder.build()
            val call: Call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // TODO HW
                }

                override fun onResponse(call: Call, response: Response) {
                    //if (response.isSuccessful) { }
                    if (response.code in 200..299 && response.body != null) {
                        response.body?.let {
                            val responseString = it.string()
                            val weatherDTO = Gson().fromJson(responseString, WeatherDTO::class.java)
                            weatherLocal.feelsLike = weatherDTO.fact.feelsLike
                            weatherLocal.temperature = weatherDTO.fact.temp
                            requireActivity().runOnUiThread {
                                renderData(weatherLocal)
                            }
                            Log.d("@@@", "${responseString}")
                        }
                    } else {
                        // TODO HW
                    }
                }
            })
        }
    }

    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: Weather,
        weatherDTO: WeatherDTO
    ) {
        requireActivity().runOnUiThread {
            renderData(weatherLocal.apply {
                weatherLocal.feelsLike = weatherDTO.fact.feelsLike
                weatherLocal.temperature = weatherDTO.fact.temp
            })
        }
    }

    private fun renderData(weather: Weather) {

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
