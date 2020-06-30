package com.example.weatheracc.repository.local

import androidx.room.*
import com.example.weatheracc.models.DailyWeather
import com.example.weatheracc.models.WeatherForecast
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherForecastDao: WeatherForecast): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherForecastDao: List<WeatherForecast>): List<Long>

    @Update
    suspend fun update(weatherForecastDao: WeatherForecast): Int

    @Delete
    suspend fun delete(weatherForecastDao: WeatherForecast): Int

    @Query("SELECT * FROM weather_forecast")
    fun getAllFlow(): Flow<List<WeatherForecast>>

    @Query("SELECT * FROM weather_forecast")
    suspend fun getAll(): List<WeatherForecast>


    //==

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(dailyWeather: DailyWeather): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(dailyWeather: List<DailyWeather>): List<Long>

    @Update
    suspend fun updateDaily(dailyWeather: DailyWeather): Int

    @Delete
    suspend fun delete(dailyWeather: DailyWeather): Int

    @Query("SELECT * FROM daily_weather_forecast")
    fun getAllFlowDaily(): Flow<List<DailyWeather>>

    @Query("SELECT * FROM daily_weather_forecast")
    suspend fun getAllDaily(): List<DailyWeather>

}