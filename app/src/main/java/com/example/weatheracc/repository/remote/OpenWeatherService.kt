package com.example.weatheracc.repository.remote

import com.example.weatheracc.models.*
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherService {

    @GET("data/2.5/weather?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getWeatherByCityId(
        @Query("id") cityId: Long,
        @Query("units") units: String = "metric"
    ): WeatherForecast

    @GET("data/2.5/group?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getWeatherByCityIdList(
        @Query("id") cityIdList: String,
        @Query("units") units: String = "metric"
    ) : WeatherCityListResponse

    @GET("data/2.5/weather?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getWeatherByLoc(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): WeatherForecast

    @GET("data/2.5/find?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun findCityWeatherByName(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric"
    ): FindCityWeatherResponse

    @GET("data/2.5/forecast?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun findHourlyWeatherByLoc(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): FindHourlyWeatherResponse

    @GET("data/2.5/onecall?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getDailyWeatherByCityId(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): FindCityWeeklyWeatherResponse
}