package com.robertrussell.miguel.openweather.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robertrussell.miguel.openweather.R
import com.robertrussell.miguel.openweather.model.entity.WeatherInformation
import com.robertrussell.miguel.openweather.utils.Utils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun OWListItem(info: WeatherInformation) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val formatter = DateTimeFormatter.ofPattern("HH")
            val current = LocalDateTime.now().format(formatter)
            val isDayTime = current.toInt() in 6..17

            val weatherImage = when (info.weatherDescription) {
                "few clouds" -> {
                    if (isDayTime)
                        R.drawable.few_clound_morning
                    else
                        R.drawable.few_cloud_night
                }

                "scattered clouds" -> R.drawable.scattered_clouds
                "broken clouds" -> R.drawable.broken_clouds
                "shower rain" -> R.drawable.shower_rain
                "rain", "moderate rain" -> {
                    if (isDayTime)
                        R.drawable.rain_morning
                    else
                        R.drawable.rain_night
                }

                "thunderstorm" -> R.drawable.thunderstorm
                "snow" -> R.drawable.snow
                "mist" -> R.drawable.mist
                "clear sky" -> {
                    if (isDayTime)
                        R.drawable.clear_sky_morning
                    else
                        R.drawable.clear_sky_night
                }

                else -> {
                    R.drawable.loading
                }
            }

            Image(
                painter = painterResource(id = weatherImage),
                contentDescription = "weatherDescription",
                Modifier
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    .height(50.dp)
            )

            Column(
                modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${info.name}, ${info.sysCountry}",
                    modifier = Modifier,
                    style = TextStyle(
                        color = Color(0xFF302D2D),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                val temp = info.mainTemp?.let {
                    Utils.convertFromKelvinToCelsius(it.toFloat())
                }

                Text(
                    text = "$temp℃",
                    modifier = Modifier,
                    style = TextStyle(
                        color = Color(0xFF302D2D),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                info.weatherDescription?.capitalize()?.let {
                    Text(
                        text = it,
                        modifier = Modifier,
                        style = TextStyle(
                            color = Color(0xFF302D2D),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }

                Text(
                    text = "Sunrise: ${info.sysSunrise?.let { Utils.convertUnixToDate(it) }}",
                    modifier = Modifier,
                    style = TextStyle(
                        color = Color(0xFF302D2D),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Text(
                    text = "Sunset: ${info.sysSet?.let { Utils.convertUnixToDate(it) }}",
                    modifier = Modifier,
                    style = TextStyle(
                        color = Color(0xFF302D2D),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}