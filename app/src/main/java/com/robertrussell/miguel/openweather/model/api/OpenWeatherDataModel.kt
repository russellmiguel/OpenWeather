package com.robertrussell.miguel.openweather.model.api

import com.google.gson.annotations.SerializedName

data class OpenWeatherDataModel(
    @SerializedName("coord") val coordinates: Coordinates?,
    @SerializedName("weather") val weather: List<Weather>?,
    @SerializedName("main") val main: Main?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("cod") val cod: String
)

data class Coordinates(
    @SerializedName("lon") val lon: String?,
    @SerializedName("lat") val lat: String?
)

data class Weather(
    @SerializedName("id") val id: String?,
    @SerializedName("main") val main: String?,
    @SerializedName("description") val description: String?
)

data class Main(
    @SerializedName("temp") val temp: String?
)

data class Sys(
    @SerializedName("id") val id: Long,
    @SerializedName("country") val country: String?,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)
