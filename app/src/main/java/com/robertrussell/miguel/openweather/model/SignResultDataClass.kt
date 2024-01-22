package com.robertrussell.miguel.openweather.model

data class SignResultDataClass(
    val id: Long = 0,
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    var errorMessage: String = ""
)
