package com.robertrussell.miguel.openweather.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.robertrussell.miguel.openweather.model.dao.UserDao
import com.robertrussell.miguel.openweather.model.dao.WeatherInformationDao
import com.robertrussell.miguel.openweather.model.entity.User
import com.robertrussell.miguel.openweather.model.entity.WeatherInformation

@Database(entities = [User::class, WeatherInformation::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun weatherDao(): WeatherInformationDao
}
