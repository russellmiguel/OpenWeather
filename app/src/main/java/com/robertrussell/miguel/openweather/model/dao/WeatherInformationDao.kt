package com.robertrussell.miguel.openweather.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.robertrussell.miguel.openweather.model.entity.WeatherInformation

@Dao
interface WeatherInformationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(info: WeatherInformation): Long

    @Query("SELECT * FROM weather_information")
    fun getAllWeatherInfo(): List<WeatherInformation>
}
