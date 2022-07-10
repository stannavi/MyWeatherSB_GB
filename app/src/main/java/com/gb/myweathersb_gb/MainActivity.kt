package com.gb.myweathersb_gb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gb.myweathersb_gb.databinding.ActivityMainBinding
import com.gb.myweathersb_gb.viewmodel.WeatherListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
    }
}