package com.robertrussell.miguel.openweather.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.robertrussell.miguel.openweather.model.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers() : List<User>

    @Query("SELECT * FROM users WHERE username LIKE :userName AND password LIKE :password")
    suspend fun getUser(userName: String, password: String): User
}