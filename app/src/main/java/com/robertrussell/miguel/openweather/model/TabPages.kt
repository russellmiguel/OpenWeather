package com.robertrussell.miguel.openweather.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

import com.robertrussell.miguel.openweather.view.CurrentInfoPage
import com.robertrussell.miguel.openweather.view.HistoryInfoPage
import com.robertrussell.miguel.openweather.viewmodel.OpenWeatherViewModel

typealias composableScreen = @Composable () -> Unit

sealed class TabPages(
    val title: String,
    val icons: ImageVector
) {
    data object CurrentInfo :
        TabPages(
            title = "Current Information",
            icons = Icons.Default.Home
        )

    data object HistoryInfo :
        TabPages(
            title = "History Information List",
            icons = Icons.Default.Refresh
        )
}
