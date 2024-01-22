package com.robertrussell.miguel.openweather.view

import android.Manifest
import android.annotation.SuppressLint
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

    var PERMISSIONS_REQUEST_CODE: Int = 101

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

    private val openWeatherViewModel by viewModels<OpenWeatherViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return OpenWeatherViewModel(database.weatherDao()) as T
                }
            }
        }
    )

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_CODE
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
                    openWeatherViewModel.setLocation(location)
                    Log.d("MainActivity", "Location Longitude: ${location.longitude} Latitude: ${location.latitude}")
                }

            }

        setContent {
            OpenWeatherMainPage(signViewModel, openWeatherViewModel)
        }
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
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
                                openWeatherViewModel.setLocation(location)
                                Log.d("MainActivity:onRequestPermissionsResult()", "Location Longitude: ${location.longitude} Latitude: ${location.latitude}")
                            }

                        }
                } else {
                    Log.e("MainActivity", "Location permission denied.")
                }
            }
        }
    }
}
