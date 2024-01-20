package com.robertrussell.miguel.openweather.domain.repository

import com.robertrussell.miguel.openweather.model.api.OpenWeatherDataModel
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.service.OWServiceBuilder
import com.robertrussell.miguel.openweather.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class OpenWeatherRepository {

    suspend fun getCurrentWeatherInformation(
        latitude: String,
        longitude: String,
        appId: String
    ): Flow<Response<OpenWeatherDataModel>> = flow {
        delay(1000)

        try {
            emit(Response.Loading)

            val response = OWServiceBuilder.getBuildService()
                .getCurrentWeatherInfo(
                    latitude,
                    longitude,
                    appId
                )

            emit(Response.Success(data = response.body()))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}
