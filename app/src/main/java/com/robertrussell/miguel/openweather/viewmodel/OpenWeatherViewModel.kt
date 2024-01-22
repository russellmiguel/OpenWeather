package com.robertrussell.miguel.openweather.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertrussell.miguel.openweather.domain.repository.OpenWeatherRepository
import com.robertrussell.miguel.openweather.model.api.OpenWeatherDataModel
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.model.dao.WeatherInformationDao
import com.robertrussell.miguel.openweather.model.entity.WeatherInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OpenWeatherViewModel(private val weatherDao: WeatherInformationDao) : ViewModel() {

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

            /**
             * Repository call.
             */
            owRepository.getCurrentWeatherInformation(
                _location.latitude.toString(),
                _location.longitude.toString(),
                appId
            ).collectLatest {
                _responseState.value = it
            }

            /**
             * Save information to local db.
             */
            val _weatherInfo = responseState.value as Response.Success
            val weatherInfo = WeatherInformation(
                coordinatesLongitude = _weatherInfo.data?.coordinates?.lon,
                coordinatesLatitude = _weatherInfo.data?.coordinates?.lat,
                weatherId = _weatherInfo.data?.weather?.get(0)?.id,
                weatherMain = _weatherInfo.data?.weather?.get(0)?.main,
                weatherDescription = _weatherInfo.data?.weather?.get(0)?.description,
                mainTemp = _weatherInfo.data?.main?.temp,
                sysId = _weatherInfo.data?.sys?.id,
                sysCountry = _weatherInfo.data?.sys?.country,
                sysSunrise = _weatherInfo.data?.sys?.sunrise,
                sysSet = _weatherInfo.data?.sys?.sunset,
                id = _weatherInfo.data?.id,
                name = _weatherInfo.data?.name,
                cod = _weatherInfo.data?.cod
            )
            weatherDao.insertWeatherInfo(weatherInfo).also {
                val weather = weatherDao.getAllWeatherInfo()
            }
        }
    }

    fun getWeatherHistory(): List<WeatherInformation> {
        return weatherDao.getAllWeatherInfo()
    }

    fun setLocation(location: Location) {
        _location = location
    }
}
