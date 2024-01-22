package com.robertrussell.miguel.openweather.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.robertrussell.miguel.openweather.R
import com.robertrussell.miguel.openweather.viewmodel.OpenWeatherViewModel

@Composable
fun HistoryInfoPage(weatherViewModel: OpenWeatherViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.info_page_bg),
                contentScale = ContentScale.FillHeight
            )
            .background(Color.White.copy(alpha = 0.65f))
    ) {
        val _weatherInformation = weatherViewModel.getWeatherHistory()
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            reverseLayout = false
        ) {
            items(
                items = _weatherInformation,
                itemContent = {
                    OWListItem(info = it)
                }
            )
        }
    }
}
