package com.example.weatheracc.di.modules

import androidx.lifecycle.ViewModel
import com.example.weatheracc.viewModels.CitySearchViewModel
import com.example.weatheracc.viewModels.DetailScreenViewModel
import com.example.weatheracc.viewModels.ForecastListViewModel
import com.example.weatheracc.viewModels.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(key = ForecastListViewModel::class)
    abstract fun bindForecastListViewModel(viewModel: ForecastListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = CitySearchViewModel::class)
    abstract fun bindCitySearchViewModel(viewModel: CitySearchViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = DetailScreenViewModel::class)
    abstract fun bindDetailScreenVieModel(viewModel: DetailScreenViewModel): ViewModel
}