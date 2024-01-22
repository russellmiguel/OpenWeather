package com.robertrussell.miguel.openweather.service

import com.robertrussell.miguel.openweather.model.api.OpenWeatherDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OWServiceInterface {

    @GET("data/2.5/weather?")
    suspend fun getCurrentWeatherInfo(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appId") appId: String
    ): Response<OpenWeatherDataModel>

}
