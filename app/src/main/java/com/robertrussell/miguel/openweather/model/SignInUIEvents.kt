package com.robertrussell.miguel.openweather.model

sealed class SignInUIEvents {

    data class UsernameChanged(val user: String) : SignInUIEvents()
    data class PasswordChanged(val pass: String) : SignInUIEvents()

    object SignInButtonClicked : SignInUIEvents()
}
