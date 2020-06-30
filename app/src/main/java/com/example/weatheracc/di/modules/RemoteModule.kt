package com.example.weatheracc.di.modules

//import com.example.weatheracc.BuildConfig
import com.example.weatheracc.repository.remote.AutocompleteCitiesService
import com.example.weatheracc.repository.remote.OpenWeatherService
import com.example.weatheracc.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideOpenWeatherService(okHttpClient: OkHttpClient) : OpenWeatherService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(OpenWeatherService::class.java)

    @Provides
    @Singleton
    fun provideAutocompleteCitiesService(okHttpClient: OkHttpClient) : AutocompleteCitiesService = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(AutocompleteCitiesService::class.java)

    @Provides
    @Singleton
    fun provideLaggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
}