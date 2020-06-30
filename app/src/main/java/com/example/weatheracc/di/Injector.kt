//package com.example.weatheracc.di
//
//import android.content.Context
//import android.content.SharedPreferences
//import com.example.weatheracc.repository.Repository
//import com.example.weatheracc.repository.local.AppDatabase
//import com.example.weatheracc.repository.local.WeatherForecastDao
//import com.example.weatheracc.repository.remote.OpenWeatherService
//import com.example.weatheracc.viewModels.ViewModelFactory
//
//
//object Injector {
//    private lateinit var appDatabase: AppDatabase
//    private lateinit var repository: Repository
//    private lateinit var factory: ViewModelFactory
//    private lateinit var sharedPreferences: SharedPreferences
//
//    fun provideFactory(context: Context) =
//        if (::factory.isInitialized) factory
//        else ViewModelFactory(provideRepository(context), sharedPreferences).also { factory = it }
//
//    private fun provideRepository(context: Context) =
//        if (::repository.isInitialized) repository
//        else Repository( provideWeatherService(context), provideWeatherDao(context)).also { repository = it }
//
//    private fun provideWeatherDao(context: Context): WeatherForecastDao =
//        if (::appDatabase.isInitialized) appDatabase.weatherForecastDao()
//        else AppDatabase.getInstance(context).let {
//            appDatabase = it
//            it.weatherForecastDao()
//        }
//
//
//}