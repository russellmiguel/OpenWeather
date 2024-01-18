package com.robertrussell.miguel.openweather.model

data class SignInValues(
    var userName: String = "",
    var password: String = "",

    var userNameError :Boolean = false,
    var passwordError : Boolean = false
)
