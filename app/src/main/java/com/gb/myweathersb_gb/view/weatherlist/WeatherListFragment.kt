package com.gb.myweathersb_gb.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gb.myweathersb_gb.databinding.FragmentWeatherListBinding
import com.gb.myweathersb_gb.viewmodel.AppState

class WeatherListFragment : Fragment() {
    companion object {
        fun newInstance() = WeatherListFragment()
    }

    lateinit var binding: FragmentWeatherListBinding
    lateinit var viewModel: WeatherListVeiwModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListVeiwModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                Toast.makeText(requireContext(), "РАБОТАЕТ $t", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.sentRequest()
    }
}