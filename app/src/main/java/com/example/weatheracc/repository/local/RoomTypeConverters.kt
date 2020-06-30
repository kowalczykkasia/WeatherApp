package com.example.weatheracc.repository.local

import androidx.room.TypeConverter
import com.example.weatheracc.models.AutocompleteCities
import com.example.weatheracc.models.Name
import com.example.weatheracc.models.Weather
import com.google.gson.Gson

class RoomTypeConverters {

    @TypeConverter
    fun weatherToJson(value: List<Weather>?): String? = value?.let { Gson().toJson(value) }

    @TypeConverter
    fun jsonToWeather(value: String?): List<Weather>? =
        value?.let { Gson().fromJson(value, Array<Weather>::class.java) as Array<Weather> }?.toList()

    @TypeConverter
    fun cityToJson(value: List<Name>?) : String? = value?.let { Gson().toJson(value) }

    @TypeConverter
    fun jsonToCity(value : String?) : List<Name>? =
        value?.let { Gson().fromJson(value, Array<Name>::class.java) as Array<Name> }?.toList()

}