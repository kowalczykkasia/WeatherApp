//package com.example.weatheracc.repository.remote
//
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface OpenDailyWeatherService {
//
//    @GET("data/2.5/forecast?appid=15646a06818f61f7b8d7823ca833e1ce")
//    suspend fun getWeatherByCityId(
//        @Query("id") cityId: Long,
//        @Query("units") units: String = "metric"
//    ) :
//}