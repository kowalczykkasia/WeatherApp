package com.example.weatheracc.repository

import com.example.weatheracc.models.DailyWeather
import com.example.weatheracc.models.Units
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.repository.local.AutocompleteCitiesDao
import com.example.weatheracc.repository.local.WeatherForecastDao
import com.example.weatheracc.repository.remote.AutocompleteCitiesService
import com.example.weatheracc.repository.remote.OpenWeatherService

class Repository(
    private val openWeatherService: OpenWeatherService,
    private val weatherForecastDao: WeatherForecastDao,
    private val autocompleteCities : AutocompleteCitiesService,
    private val autocompleteCitiesDao: AutocompleteCitiesDao
) {

    fun getWeatherListFlow() = weatherForecastDao.getAllFlow()

   suspend fun getWeatherList() = weatherForecastDao.getAll()

    fun getWeeklyWeatherListFlow() = weatherForecastDao.getAllFlowDaily()

    suspend fun getWeeklyWeatherList() = weatherForecastDao.getAllDaily()

    suspend fun fetchWeatherByCityIdList(cityIdList: List<Long>,
                                         units: Units = Units.METRIC
    ) = openWeatherService.getWeatherByCityIdList(
        cityIdList.fold("", {acc: String, cityId: Long ->
            "$acc$cityId,"
        }), units.name.toLowerCase()
    ).also { weatherForecastDao.insert(it.list) }

    suspend fun findCityByName(cityName: String, units: Units = Units.METRIC) =
        openWeatherService.findCityWeatherByName(cityName, units.name.toLowerCase())

     suspend fun getWeatherByLoc(lat: Double, lon: Double, units: Units = Units.METRIC) =
        openWeatherService.getWeatherByLoc(lat, lon, units.name.toLowerCase())

    suspend fun storeCity(weatherForecast: WeatherForecast) = weatherForecastDao.insert(weatherForecast)

    suspend fun findCityByCityId(lat: Double, lon: Double, units: Units = Units.METRIC) =
        openWeatherService.getDailyWeatherByCityId(lat, lon, units.name.toLowerCase())

    suspend fun findHourlyWeatherByLoc(lat: Double, lon: Double, units: Units = Units.METRIC) =
        openWeatherService.findHourlyWeatherByLoc(lat, lon, units.name.toLowerCase())

    suspend fun storeCityWeekly(dailyWeather: DailyWeather) = weatherForecastDao.insertDaily(dailyWeather)

    suspend fun deleteCity(weatherForecast: WeatherForecast) = weatherForecastDao.delete(weatherForecast)

    suspend fun findCityNameByPrefix(cityName: String) =
        autocompleteCities.getCitiesBySomeLetters(cityName)

}