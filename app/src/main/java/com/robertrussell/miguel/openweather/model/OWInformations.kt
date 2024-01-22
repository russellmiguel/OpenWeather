package com.robertrussell.miguel.openweather.model

data class OWInformations(
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
