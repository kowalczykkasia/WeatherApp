package com.example.weatheracc.repository.remote

import com.example.weatheracc.models.AutocompleteCitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AutocompleteCitiesService {

    @GET("maps/api/place/autocomplete/json?key=AIzaSyD1sfgWolkpOPKk42FUZr8t3hqvssYxzeE")
    suspend fun getCitiesBySomeLetters(
        @Query("input") cityName: String
    ) : AutocompleteCitiesResponse
}