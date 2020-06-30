package com.example.weatheracc.di.components



import com.example.weatheracc.di.modules.*
import com.example.weatheracc.di.modules.ActivityModule
import com.example.weatheracc.di.modules.FragmentModule
import com.example.weatheracc.WeatherApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
    AndroidSupportInjectionModule::class,
    RepositoryModule::class,
    DatabaseModule::class,
    FragmentModule::class,
    ActivityModule::class,
    ContextModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    RemoteModule::class
    ]
)

interface AppComponent : AndroidInjector<WeatherApplication>{

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApplication>()
}