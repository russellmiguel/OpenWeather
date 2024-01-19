package com.robertrussell.miguel.openweather.model

sealed class SignUpUIEvent {
    data class NameChanged(val name: String) : SignUpUIEvent()
    data class EmailChanged(val email: String) : SignUpUIEvent()
    data class UsernameChanged(val user: String) : SignUpUIEvent()
    data class PasswordChanged(val pass: String) : SignUpUIEvent()

    object SignUpButtonClicked : SignUpUIEvent()
}