//package com.example.weatheracc.viewModels
//
//import android.content.SharedPreferences
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.weatheracc.repository.Repository
//
//@Suppress("UNCHECKED_CAST")
//class ViewModelFactory(private val repository: Repository, private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return when (modelClass) {
//            ForecastListViewModel::class.java -> {
//                ForecastListViewModel(repository, sharedPreferences) as T
//            }
//            SplashViewModel::class.java -> {
//                SplashViewModel(repository, sharedPreferences) as T
//            }
//            else -> {
//                throw IllegalArgumentException("unknown model class $modelClass")
//            }
//        }
//    }
//}