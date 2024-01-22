package com.robertrussell.miguel.openweather.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.robertrussell.miguel.openweather.model.OWInformations
import com.robertrussell.miguel.openweather.viewmodel.OpenWeatherViewModel

@Composable
fun HistoryInfoPage(weatherViewModel: OpenWeatherViewModel) {

    val weatherInformation: MutableList<OWInformations> = mutableListOf()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        val _weatherInformation = weatherViewModel.getWeatherHistory()
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
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


//
//Column(
//modifier = Modifier
//.fillMaxWidth()
//.padding(5.dp)
//.border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
//verticalArrangement = Arrangement.Center,
//horizontalAlignment = Alignment.Start
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(10.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.clear_sky_morning),
//            contentDescription = "weatherDescription",
//            Modifier
//                .padding(0.dp, 0.dp, 10.dp, 0.dp)
//                .height(50.dp)
//        )
//
//        Column(
//            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "US, Mountain View",
//                modifier = Modifier,
//                style = TextStyle(
//                    color = Color(0xFF302D2D),
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Normal
//                )
//            )
//            Text(
//                text = "36.4℃",
//                modifier = Modifier,
//                style = TextStyle(
//                    color = Color(0xFF302D2D),
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            )
//        }
//
//        Column(
//            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Clear Sky",
//                modifier = Modifier,
//                style = TextStyle(
//                    color = Color(0xFF302D2D),
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Normal
//                )
//            )
//
//            Text(
//                text = "Sunrise: 05:27 AM",
//                modifier = Modifier,
//                style = TextStyle(
//                    color = Color(0xFF302D2D),
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Normal
//                )
//            )
//
//            Text(
//                text = "Sunset: 06:17 PM",
//                modifier = Modifier,
//                style = TextStyle(
//                    color = Color(0xFF302D2D),
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Normal
//                )
//            )
//        }
//    }
//}
