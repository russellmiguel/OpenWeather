package com.robertrussell.miguel.openweather.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.robertrussell.miguel.openweather.domain.AppDataBase
import com.robertrussell.miguel.openweather.viewmodel.OpenWeatherViewModel
import com.robertrussell.miguel.openweather.viewmodel.SignViewModel


class MainActivity : ComponentActivity() {

    private lateinit var owViewModel: OpenWeatherViewModel

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "open_weather_db"
        ).allowMainThreadQueries().build()
    }

    private val signViewModel by viewModels<SignViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SignViewModel(database.userDao()) as T
                }
            }
        }
    )

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //private var _location = Location("dummyprovider")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        owViewModel = ViewModelProvider(this)[OpenWeatherViewModel::class.java]

        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                    CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(this, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    owViewModel.setLocation(location)
                    Log.d("MainActivity", "Location Longitude: ${location.longitude} Latitude: ${location.latitude}")
                }

            }

        setContent {
            OpenWeatherMainPage(signViewModel, owViewModel)
        }
    }
}
