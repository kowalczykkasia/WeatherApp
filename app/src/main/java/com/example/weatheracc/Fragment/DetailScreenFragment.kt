package com.example.weatheracc.ui.main.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.weatheracc.adapters.DetailDailyInfoAdapter
import com.example.weatheracc.adapters.HourlyWeatherAdapter
import com.example.weatheracc.adapters.WeeklyWeatherAdapter
import com.example.weatheracc.models.DailyWeather
import com.example.weatheracc.models.DetailDailyInfo
import com.example.weatheracc.models.HourlyWeather
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.viewModels.DetailScreenViewModel
import com.example.weatheracc.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.daily_weather_detail.view.*
import kotlinx.android.synthetic.main.detail_screen_fragment2.*
import kotlinx.android.synthetic.main.detail_screen_fragment2.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DetailScreenFragment : DaggerFragment() {

   @Inject
   lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailScreenViewModel>(){factory}

    private val weeklyWeatherAdapter by lazy {

        WeeklyWeatherAdapter{ dailyWeather: DailyWeather, view: View ->

            Rw.forEach { view -> view.itemBackground.setBackgroundResource(R.drawable.edit_round) }

            view.itemBackground.setBackgroundResource(R.drawable.rounded_item)
            val list = mutableListOf<DetailDailyInfo>()
            context?.let {
                viewModel.initList(list, dailyWeather, it)
            }
            val adapter = DetailDailyInfoAdapter(list)
            recyclerViewWithInfo.adapter = adapter
            RW_hours.adapter = HourlyWeatherAdapter(viewModel.initHoursList(dailyWeather))
            line.visibility = View.VISIBLE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_screen_fragment2, container, false).apply {

            Rw.adapter = weeklyWeatherAdapter
            setContetntView(time_date2, city_country_name, temperature, description, sun,
                rain, backgroundClouds, gradient, thunder, backgroundStars, titleCountry)

            bookmark.setOnClickListener {
                if (bookmarked.visibility == View.GONE)
                    bookmarked.visibility = View.VISIBLE
                else
                    bookmarked.visibility = View.GONE
            }

            search.setOnClickListener {
                findNavController().navigate(DetailScreenFragmentDirections.actionDetailScreenToCitySearchFragment())
            }

            with(viewModel){
                weatherForecast.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    weeklyWeatherAdapter.submitList(it)
                })
                time.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    val timeZone = TimeZone.getTimeZone(it.toString())
                    val localDateTime = LocalDateTime.now(ZoneId.of(timeZone.id))
                    var s  = "${localDateTime.format(DateTimeFormatter.ofPattern("HH:mm ",Locale("", "en")))}"
                    s += "${ZonedDateTime.now(timeZone.toZoneId()).month.toString().toLowerCase()} ${ZonedDateTime.now(timeZone.toZoneId()).dayOfMonth.toString().toLowerCase()}"
                    time_date2.text = s
                })
            }

        }
    }

    private fun setContetntView(
        time_date: TextView, city_country_name: TextView,
        temperature: TextView,
        description: TextView, sun: ImageView, backgroundImage2: ImageView, backgroundClouds: ImageView, gradient: ImageView,
        thunder: ImageView, backgroundStars: ImageView, titleCountry : TextView
    ){
        val weatherInfo = arguments?.getParcelable<WeatherForecast>("cityCountryName")

        if (weatherInfo != null) {
            val lat = weatherInfo.coord.lat
            val lon = weatherInfo.coord.lon

            viewModel.searchCity(lat, lon)
        }

        val lon = weatherInfo?.coord?.lon!! * 4 / 60
        val hour = LocalDateTime.now().hour - 1 + lon

        if (hour > 19 || hour < 5) {
            gradient.setImageResource(R.drawable.night)
            gradient.visibility = View.VISIBLE
            backgroundStars.visibility = View.VISIBLE
            sun.visibility = View.GONE
        }else if (weatherInfo?.weather?.firstOrNull()?.main == "Rain") {
            backgroundImage2.visibility = View.VISIBLE
            sun.visibility = View.GONE
            gradient.setImageResource(R.drawable.gradient)
            gradient.visibility = View.VISIBLE
            backgroundClouds.visibility = View.VISIBLE
            backgroundClouds.setImageResource(R.drawable.clouds_rain)
        }
        if(weatherInfo?.weather?.firstOrNull()?.main == "Clouds")
            backgroundClouds.visibility = View.VISIBLE
        if (weatherInfo?.weather?.firstOrNull()?.description.equals("thunder")){
            thunder.visibility = View.VISIBLE
        }

        val locale = Locale("", weatherInfo?.sys?.country)
        city_country_name.setText("${weatherInfo?.name}, ${locale.getDisplayCountry()}")
        temperature.setText("${weatherInfo?.main?.temp?.toInt()}°")
        titleCountry.text = city_country_name.text
        description.setText("${weatherInfo?.weather?.firstOrNull()?.description}.The highest will be ${weatherInfo?.main?.temp_max} ° Showers tonight with a low of ${weatherInfo?.main?.temp_min} ° ")
    }
}
