package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.DailyWeather
import kotlinx.android.synthetic.main.daily_weather_detail.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class WeeklyWeatherAdapter (private val listener : (DailyWeather, View) -> Unit
) : ListAdapter<DailyWeather, WeeklyWeatherAdapter.WeeklyWeatherViewHolder>(DIFF_CALLBACK) {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyWeatherViewHolder =
              WeeklyWeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.daily_weather_detail, parent, false))


        override fun onBindViewHolder(holder: WeeklyWeatherViewHolder, position: Int) {
                holder.bind(getItem(position), listener)
        }



        companion object {
                private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DailyWeather>(){

                        override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather
                        ): Boolean =
                                oldItem.dt == newItem.dt

                        override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather
                        ): Boolean =
                                oldItem == newItem
                }
        }

        class WeeklyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

                fun bind(city: DailyWeather, listener: (DailyWeather, View) -> Unit) {
                        itemView.apply {
                                val date = Date(city.dt * 1000L)
                                val today = Calendar.getInstance().time
                                val formatter = SimpleDateFormat("EEEE", Locale("en"))
                                formatter.timeZone = TimeZone.getTimeZone("GMT-4")
                                when(city.weather.firstOrNull()?.main){
                                        "Clear" -> {
                                                conditions_emoji.setImageResource(R.drawable.ic_sun)
                                        }
                                        "Clouds" ->{
                                                conditions_emoji.setImageResource(R.drawable.ic_clouds)
                                        }
                                        "Rain" ->{
                                                conditions_emoji.setImageResource(R.drawable.ic_group_5)
                                        }
                                        "Snow" -> {
                                                conditions_emoji.setImageResource(R.drawable.ic_shape)
                                        }
                                        "Thunder" -> {
                                                conditions_emoji.setImageResource(R.drawable.ic_group_6)
                                        }
                                }
                                if(formatter.format(date).equals(formatter.format(today)))
                                        day.text = "Today"
                                else
                                        day.text = formatter.format(date)

                                temp.text = "${city.temp.temp.roundToInt()}°"

                            setOnClickListener {

                                    listener(city, itemView)
                            }

                        }
                }
        }
}