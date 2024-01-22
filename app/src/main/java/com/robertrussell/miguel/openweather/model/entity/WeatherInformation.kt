package com.robertrussell.miguel.openweather.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("weather_information")
data class WeatherInformation(
    @PrimaryKey(autoGenerate = true) val rowId: Long = 0,
    val coordinatesLongitude: String?,
    val coordinatesLatitude: String?,
    val weatherId: String?,
    val weatherMain: String?,
    val weatherDescription: String?,
    val mainTemp: String?,
    val sysId: Long?,
    val sysCountry: String?,
    val sysSunrise: Long?,
    val sysSet: Long?,
    val id: Long?,
    val name: String?,
    val cod: String?
)
