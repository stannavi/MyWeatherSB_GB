package com.gb.myweathersb_gb

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.gb.myweathersb_gb.databinding.ActivityMainBinding
import com.gb.myweathersb_gb.utils.SP_BD_NAME_IS_RUSSIAN
import com.gb.myweathersb_gb.utils.SP_KEY_IS_RUSSIAN
import com.gb.myweathersb_gb.view.weatherlist.CitiesListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance()).commit()
        }
        val sp = getSharedPreferences(SP_BD_NAME_IS_RUSSIAN, Context.MODE_PRIVATE)
        val spActivity = getPreferences(Context.MODE_PRIVATE)
        val spApp = getDefaultSharedPreferences(this)

        val isRussian = sp.getBoolean(SP_KEY_IS_RUSSIAN, true)
        val editor = sp.edit()
        editor.putBoolean(SP_KEY_IS_RUSSIAN, isRussian)
        editor.apply()

        sp.edit().apply{
            putBoolean(SP_KEY_IS_RUSSIAN, isRussian)
            apply()
        }


    }

}