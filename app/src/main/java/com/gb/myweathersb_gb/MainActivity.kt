package com.gb.myweathersb_gb

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.gb.myweathersb_gb.databinding.ActivityMainBinding
import com.gb.myweathersb_gb.view.contentprovider.ContentProviderFragment
import com.gb.myweathersb_gb.view.maps.MapsFragment
import com.gb.myweathersb_gb.view.room.WeatherHistoryListFragment
import com.gb.myweathersb_gb.view.weatherlist.CitiesListFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.myRoot)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance()).commit()
        }
        pushNotification("title","body")
        getToken()
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("@@@", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            //Get new FCM registration token
            val token = task.result
            Log.d("@@@", "$token")
        })
    }

    val CHANNEL_HIGH_ID = "channel_high"
    val CHANNEL_LOW_ID = "channel_low"
    val NOTIFICATION_ID1 = 1
    val NOTIFICATION_ID2 = 2

    private fun pushNotification(title:String, body:String){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(this,CHANNEL_HIGH_ID).apply {
            setContentTitle(title)
            setContentText(body)
            setSmallIcon(R.drawable.ic_kotlin_logo)
            priority = NotificationCompat.PRIORITY_MAX
            //intent = PendingIntent() TODO HW по клику на push - открыть MainActivity
        }

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channelHigh = NotificationChannel(CHANNEL_HIGH_ID,CHANNEL_HIGH_ID,
                NotificationManager.IMPORTANCE_HIGH)
            channelHigh.description = "Канал для бла бла бла"
            notificationManager.createNotificationChannel(channelHigh)
        }

        notificationManager.notify(NOTIFICATION_ID1,notification.build())


        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channelLow = NotificationChannel(CHANNEL_LOW_ID,CHANNEL_LOW_ID, NotificationManager.IMPORTANCE_LOW)
            channelLow.description = "Канал LOW для бла бла бла"
            notificationManager.createNotificationChannel(channelLow)
        }
        notificationManager.notify(NOTIFICATION_ID2,notification.build())

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {

            R.id.menu_history -> {
                navigationTo(WeatherHistoryListFragment())
                true
            }
            R.id.menu_content_provider -> {
                navigationTo(ContentProviderFragment())
                true
            }
            R.id.menu_google_maps -> {
                navigationTo(MapsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigationTo(fr: Fragment) {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, fr)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}