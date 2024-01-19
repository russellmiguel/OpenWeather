package com.robertrussell.miguel.openweather.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages
import com.robertrussell.miguel.openweather.viewmodel.SignViewModel

@Composable
fun OpenWeatherMainPage(viewModel: SignViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        // TODO: Check if user is logged in.
        // Change page destination if needed.

        Crossfade(targetState = Navigation.currentPage, label = "") { currentPage ->
            when (currentPage.value) {
                is Pages.SignUpScreen -> {
                    SignUpPage(viewModel)
                }

                is Pages.HomeScreen -> {
                    HomePage()
                }

                else -> {
                    SignInPage(viewModel)
                }
            }

        }
    }
}