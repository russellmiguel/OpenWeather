package com.robertrussell.miguel.openweather

import android.app.Application
import androidx.room.Room
import com.robertrussell.miguel.openweather.domain.repository.AppDataBase

class OpenWeatherApp : Application() {

    companion object {
        lateinit var database: AppDataBase
    }

    override fun onCreate() {
        super.onCreate()
        database =
            Room.databaseBuilder(
                applicationContext, AppDataBase::class.java,
                "open_weather_db"
            ).build()
    }
}