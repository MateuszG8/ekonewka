package com.example.ekonewka

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.ekonewka.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private val mainVm by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        createNotificationChannel()






        setNavMainVisibility(mainVm.isNavMainVisible)
        binding.addFB.setOnClickListener {
            setNavMainVisibility(false)
            navController.navigate(R.id.addFlower)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.NavigationViewMain, navController)

//        mainVm.insertFlower(Flower(0,"Kwiatek",false,false,false,0,0,0,0,0,0))
//        mainVm.insertFlower(Flower(0,"Kwiatuszek",false,false,false,0,0,0,0,0,0))
//        mainVm.insertFlower(Flower(0,"Palemka",false,false,false,0,0,0,0,0,0))
    }

    fun setNavMainVisibility(bool: Boolean){
        mainVm.isNavMainVisible = bool

        val isVisible = when(bool){
            true -> View.VISIBLE
            false -> View.INVISIBLE
        }

        binding.NavigationViewMain.visibility = isVisible
        binding.addFB.visibility = isVisible

    }
    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    fun scheduleNotification(date : Long)
    {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = "Zajmij się mną"
        val message = "Niektóre towje rośliny potrzebują opieki"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = date
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

    }






}