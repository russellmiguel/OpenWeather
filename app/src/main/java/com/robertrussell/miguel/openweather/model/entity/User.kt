package com.robertrussell.miguel.openweather.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["username"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val email: String,
    val username: String,
    val password: String
)
