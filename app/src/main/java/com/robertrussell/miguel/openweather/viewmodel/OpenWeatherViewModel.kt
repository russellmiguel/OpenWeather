package com.robertrussell.miguel.openweather.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.robertrussell.miguel.openweather.domain.repository.OpenWeatherRepository
import com.robertrussell.miguel.openweather.model.api.OpenWeatherDataModel
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.model.dao.UserDao
import com.robertrussell.miguel.openweather.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OpenWeatherViewModel : ViewModel() {

    private val TAG = OpenWeatherViewModel::class.simpleName
    private var owRepository = OpenWeatherRepository()
    private lateinit var _location: Location

    /**
     * Response flow.
     */
    private val _responseState =
        MutableStateFlow<Response<OpenWeatherDataModel>>(Response.Success(null))
    val responseState: StateFlow<Response<OpenWeatherDataModel>> = _responseState

    fun getCurrentWeatherInformation(
        appId: String
    ) {
        viewModelScope.launch {
            Log.d(
                TAG,
                "OpenWeatherViewModel|getCurrentWeatherInformation :: _location.lan: ${_location.latitude}"
            )
            Log.d(
                TAG,
                "OpenWeatherViewModel|getCurrentWeatherInformation :: _location.lon: ${_location.longitude}"
            )

            // repository call
            owRepository.getCurrentWeatherInformation(
                _location.latitude.toString(),
                _location.longitude.toString(),
                appId
            ).collectLatest {
                Log.d(TAG, "getCurrentWeatherInformation :: Response: $it")
                _responseState.value = it
            }
        }
    }

    fun setLocation(location: Location) {
        _location = location
    }
}