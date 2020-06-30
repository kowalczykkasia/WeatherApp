package com.example.weatheracc.di.modules

import com.example.weatheracc.repository.Repository
import com.example.weatheracc.repository.local.AppDatabase
import com.example.weatheracc.repository.remote.AutocompleteCitiesService
import com.example.weatheracc.repository.remote.OpenWeatherService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(openWeatherService: OpenWeatherService, database: AppDatabase, autocompleteCitiesService: AutocompleteCitiesService) =
        Repository(openWeatherService, database.weatherForecastDao(), autocompleteCitiesService, database.autocompleteCitiesDao())


}