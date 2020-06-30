package com.example.weatheracc.di.modules

import com.example.weatheracc.ui.main.Fragment.CitySearchFragment
import com.example.weatheracc.ui.main.Fragment.DetailScreenFragment
//import com.example.weatheracc.ui.main.Fragment.ForecastListFragment2
import com.example.weatheracc.ui.main.Fragment.ForecastListFragment
import com.example.weatheracc.ui.main.Fragment.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun bindSplashFragment() : SplashFragment

    @ContributesAndroidInjector
    internal abstract fun bindForecastListFragment() : ForecastListFragment

    @ContributesAndroidInjector
    internal abstract fun bindCitySearchFragment() : CitySearchFragment

    @ContributesAndroidInjector
    internal abstract fun bindDetailScreenFragment() : DetailScreenFragment
}