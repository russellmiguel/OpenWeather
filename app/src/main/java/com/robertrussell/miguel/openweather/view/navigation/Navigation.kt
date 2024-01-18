package com.robertrussell.miguel.openweather.view.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Pages {
    object SignInScreen: Pages()
    object SignUpScreen: Pages()
    object HomeScreen: Pages()
}
object Navigation {
    var currentPage: MutableState<Pages> = mutableStateOf(Pages.SignInScreen)

    fun navigateTo(destinationPage: Pages) {
        currentPage.value = destinationPage
    }
}
