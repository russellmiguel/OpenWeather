package com.robertrussell.miguel.openweather.domain.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.robertrussell.miguel.openweather.model.dao.UserDao
import com.robertrussell.miguel.openweather.model.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}