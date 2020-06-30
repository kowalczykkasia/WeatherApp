package com.example.weatheracc.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatheracc.models.*

@Database(entities = [WeatherForecast::class, DailyWeather::class, AutocompleteCities::class], version = 5)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherForecastDao(): WeatherForecastDao
    abstract fun autocompleteCitiesDao() : AutocompleteCitiesDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "weather_acc.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}