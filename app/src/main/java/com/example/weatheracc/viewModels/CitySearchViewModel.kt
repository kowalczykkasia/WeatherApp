package com.example.weatheracc.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheracc.models.AutocompleteCities
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.repository.Repository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class CitySearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag = "CitySearchViewModel"

    val savedCityList = MutableLiveData<List<WeatherForecast>>()
    val cityList = MutableLiveData<List<WeatherForecast>>()
    val errorMessage = MutableLiveData<String>()
    val citiesName = MutableLiveData<List<AutocompleteCities>>()

    var currentName : String = ""
    var currentCountry = ""

    init {

        repository.getWeatherListFlow()
            .onStart {
                Log.d(tag, "Flow starting")
            }
            .onCompletion {
                Log.d(tag, "Flow complete")
            }
            .catch {
                Log.d(tag, "Flow error $it")
            }
            .onEach {
                Log.d(tag, "Flow success $it")
                savedCityList.value = it
            }
            .launchIn(viewModelScope)
    }

    fun searchCityName(prefix : String){
        viewModelScope.launch {
            try {
                val result = repository.findCityNameByPrefix(prefix)
                citiesName.postValue(result.list)
            }catch (e: Exception){
                e.printStackTrace()
                errorMessage.postValue(e.toString())
            }
        }
    }

    fun searchCity(cityName: String){

        viewModelScope.launch {
            try{
                val result = repository.findCityByName(cityName)
                cityList.postValue(result.list)
            }catch (e: Exception){
                e.printStackTrace()
                errorMessage.postValue(e.toString())
            }
        }
    }

    fun findCurrentCity(lat: Double, lon: Double){
        viewModelScope.launch {
            try{
                val result = repository.getWeatherByLoc(lat, lon)
                val list =  mutableListOf<WeatherForecast>()
                currentName = result.name
                currentCountry = result.sys.country
                result.name = "Your current location"
                result.sys.country = ""
                list.add(result)
                for (weatherForecast: WeatherForecast in savedCityList.value!!){
                    list.add(weatherForecast)
                }
                savedCityList.postValue(list)
            }catch (e: Exception){
                e.printStackTrace()
                errorMessage.postValue(e.toString())
            }
        }
    }

    fun storeCity(weatherForecast: WeatherForecast){
        viewModelScope.launch {
            repository.storeCity(weatherForecast)
        }
    }


}
