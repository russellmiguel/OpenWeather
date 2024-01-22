package com.robertrussell.miguel.openweather.model

data class SignUpValues(
    var name: String = "",
    var email: String = "",
    var userName: String = "",
    var password: String = "",

    var nameError: Boolean = false,
    var emailError: Boolean = false,
    var userNameError: Boolean = false,
    var passwordError: Boolean = false,

    var errorMessage: String = ""
)
