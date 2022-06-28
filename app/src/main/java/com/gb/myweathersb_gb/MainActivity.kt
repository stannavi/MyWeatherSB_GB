package com.gb.myweathersb_gb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.gb.myweathersb_gb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn.text = "Change"
        //setContentView(R.layout.activity_main)
        //findViewById<Button>(R.id.btn).text = "Change"


    }
}