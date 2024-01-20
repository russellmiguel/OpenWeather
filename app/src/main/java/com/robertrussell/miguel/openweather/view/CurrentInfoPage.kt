package com.robertrussell.miguel.openweather.view

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robertrussell.miguel.openweather.R
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.utils.Constants
import com.robertrussell.miguel.openweather.utils.Utils
import com.robertrussell.miguel.openweather.viewmodel.OpenWeatherViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CurrentInfoPage(openWeatherViewModel: OpenWeatherViewModel) {

    val context = LocalContext.current
    openWeatherViewModel.getCurrentWeatherInformation(
        Constants.APPID
    )

    Surface(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(1f)
    ) {
        Column(
            modifier = with(Modifier) {
                fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.info_page_bg),
                        contentScale = ContentScale.FillHeight
                    )
                    .background(Color.White.copy(alpha = 0.45f))
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val response = openWeatherViewModel.responseState.collectAsStateWithLifecycle()
            when (response.value) {
                is Response.Loading -> {
                    LoadingAnimation(
                        modifier = Modifier
                            .padding(30.dp),
                        circleSize = 25.dp
                    )
                }

                is Response.Success -> {
                    val responseValue = response.value as Response.Success
                    val weather = responseValue.data?.weather?.get(0)?.main
                    val weatherDescription =
                        responseValue.data?.weather?.get(0)?.description
                    val city = responseValue.data?.sys?.country
                    val country = responseValue.data?.name
                    val temp = responseValue.data?.main?.temp?.let {
                        Utils.convertFromKelvinToCelsius(it.toFloat())
                    }

                    val formatter = DateTimeFormatter.ofPattern("HH")
                    val current = LocalDateTime.now().format(formatter)
                    val isDayTime = current.toInt() in 6..17

                    val weatherImage = when (weatherDescription) {
                        "few clouds" -> {
                            if (isDayTime)
                                R.drawable.few_clound_morning
                            else
                                R.drawable.few_cloud_night
                        }

                        "scattered clouds" -> R.drawable.scattered_clouds
                        "broken clouds" -> R.drawable.broken_clouds
                        "shower rain" -> R.drawable.shower_rain
                        "rain" -> {
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

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (weatherImage != R.drawable.loading) {
                            Image(
                                painter = painterResource(id = weatherImage),
                                contentDescription = weatherDescription
                            )
                        }

                        if (city != null && country != null) {
                            Text(
                                text = "$city, $country",
                                modifier = Modifier,
                                style = TextStyle(
                                    color = Color(0xFF302D2D),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        if (temp != null) {
                            Text(
                                text = "$temp\u2103",
                                modifier = Modifier,
                                style = TextStyle(
                                    color = Color(0xFF302D2D),
                                    fontSize = 112.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        if (weatherDescription != null) {
                            Text(
                                text = weatherDescription.capitalize(),
                                modifier = Modifier,
                                style = TextStyle(
                                    color = Color(0xFF302D2D),
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            responseValue.data?.sys?.sunrise?.let {
                                Utils.convertUnixToDate(it)
                                Text(
                                    text = "Sunrise: $it",
                                    modifier = Modifier,
                                    style = TextStyle(
                                        color = Color(0xFF302D2D),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                            }

                            responseValue.data?.sys?.sunset?.let {
                                Utils.convertUnixToDate(it)
                                Text(
                                    text = "Sunset: $it",
                                    modifier = Modifier,
                                    style = TextStyle(
                                        color = Color(0xFF302D2D),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                            }
                        }
                    }
                }

                is Response.Failure -> {

                }
            }
        }
    }
}

@Composable
@Preview
fun CurrentInfoPagePreview() {
    val viewModel: OpenWeatherViewModel = viewModel()
    CurrentInfoPage(viewModel)
}
