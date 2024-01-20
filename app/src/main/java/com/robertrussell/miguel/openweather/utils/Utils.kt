package com.robertrussell.miguel.openweather.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

object Utils {

    fun convertFromKelvinToCelsius(value: Float): Double {
        return ((value - 273.15f) * 10.0).roundToInt() / 10.0
    }

    @SuppressLint("SimpleDateFormat")
    fun convertUnixToDate(unix: Long): Any? {
//        val javaTimeStamp = unix * 1000L
//        return Date(javaTimeStamp)

        // TODO: For testing, formatted value display wrong date
        val sdf = SimpleDateFormat("KK:mm:ss aaa")
        val date = Date(unix * 1000)
        return sdf.format(date)
    }

    fun isDayTime(current: Date) {

    }
}
