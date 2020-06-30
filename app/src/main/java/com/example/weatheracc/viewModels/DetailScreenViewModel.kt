package com.example.weatheracc.viewModels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheracc.R
import com.example.weatheracc.models.*
import com.example.weatheracc.repository.Repository
import com.example.weatheracc.repository.local.getUnits
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailScreenViewModel @Inject constructor(private val repository: Repository, private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val weatherForecast = MutableLiveData<List<DailyWeather>>()
    val hourlyweatherForecast = MutableLiveData<List<HourlyWeather>>()
    val time = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()

    fun searchCity(lat: Double, lon: Double){

        viewModelScope.launch {
            try{
                val result = repository.findCityByCityId(lat, lon, sharedPreferences.getUnits())
                val fiveDaysList = mutableListOf<DailyWeather>()

                for (day : DailyWeather in result.list){
                    if (fiveDaysList.size < 5)
                        fiveDaysList.add(day)
                }
                weatherForecast.postValue(fiveDaysList)

                time.postValue(result.timezone)

                val result2 = repository.findHourlyWeatherByLoc(lat, lon, sharedPreferences.getUnits())
                hourlyweatherForecast.postValue(result2.list)

            }catch (e: Exception){
                e.printStackTrace()
                errorMessage.postValue(e.toString())
            }
        }
    }

    fun initList(list: MutableList<DetailDailyInfo>, it: DailyWeather, context: Context){

        val formatter = SimpleDateFormat("HH:mm", Locale("en"))
        formatter.timeZone = TimeZone.getTimeZone(time.value)
        list.add( DetailDailyInfo(context.getString(R.string.Sunrise), formatter.format((it.sunrise ) * 1000L)))
        list.add( DetailDailyInfo(context.getString(R.string.Sunrise), formatter.format((it.sunset) * 1000L)))
        list.add( DetailDailyInfo(context.getString(R.string.Chance_of_rain), "${it.rain}%"))
        list.add( DetailDailyInfo(context.getString(R.string.Humidity), "${it.humidity}%"))
        list.add( DetailDailyInfo(context.getString(R.string.Wind), "${it.wind_speed}m/s"))
        list.add( DetailDailyInfo(context.getString(R.string.Feels_like), "${it.feels_like.day.toInt()}°"))
        list.add( DetailDailyInfo(context.getString(R.string.pressure), "${it.pressure}hPa"))
        list.add( DetailDailyInfo(context.getString(R.string.UV_Index), it.uvi.toString()))
    }

    fun initHoursList(it: DailyWeather) : MutableList<HourlyWeather>{

        val hList = mutableListOf<HourlyWeather>()

        val today = Date(it.dt*1000L)
        val formatter = SimpleDateFormat("dd", Locale("en"))
        val hourFormatter = SimpleDateFormat("HH", Locale("en"))

        for (hour: HourlyWeather in hourlyweatherForecast.value!!){
            val thisDay = Date(hour.dt*1000L)
            if (formatter.format(today).equals(formatter.format(thisDay)) && hourFormatter.format(thisDay).toInt() > 7) {
                hList.add(hour)
            }
        }
        return hList
    }


}
