package com.robertrussell.miguel.openweather

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.robertrussell.miguel.openweather.domain.AppDataBase
import com.robertrussell.miguel.openweather.model.dao.WeatherInformationDao
import com.robertrussell.miguel.openweather.model.entity.User
import com.robertrussell.miguel.openweather.model.entity.WeatherInformation
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherInformationDaoTest {

    lateinit var database: AppDataBase
    lateinit var weatherDao: WeatherInformationDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        weatherDao = database.weatherDao()
    }

    @Test
    fun insertWeatherInfo() = runBlocking {
        val weatherInfo = WeatherInformation(
            coordinatesLongitude = "121.0359",
            coordinatesLatitude = "14.5794",
            weatherId = "802",
            weatherMain = "Clouds",
            weatherDescription = "scattered clouds",
            mainTemp = "301.75",
            sysId = 2083945,
            sysCountry = "PH",
            sysSunrise = 1705875894,
            sysSet = 1705916944,
            id = 1701966,
            name = "Mandaluyong City",
            cod = "200"
        )

        weatherDao.insertWeatherInfo(weatherInfo)

        val result = weatherDao.getAllWeatherInfo()
        Assert.assertEquals(1, result.size)

        // Check value from result
        Assert.assertEquals(weatherInfo.coordinatesLongitude, result[0].coordinatesLongitude)
        Assert.assertEquals(weatherInfo.coordinatesLatitude, result[0].coordinatesLatitude)
        Assert.assertEquals(weatherInfo.name, result[0].name)
        Assert.assertEquals(weatherInfo.sysCountry, result[0].sysCountry)
    }

    @After
    fun tearDown() {
        database.close()
    }
}