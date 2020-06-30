package com.example.weatheracc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.Units
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.repository.local.getUnits
import kotlinx.android.synthetic.main.item_saved_city.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class CitiesAdapter (
    private val listener : (WeatherForecast) -> Unit
) : ListAdapter<WeatherForecast, CitiesAdapter.CitiesViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder =
        CitiesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_saved_city, parent, false))

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) =
        holder.bind(getItem(position), listener)


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherForecast>(){

            override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast
            ): Boolean =
                oldItem == newItem
        }
    }

    class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(city: WeatherForecast, listener: (WeatherForecast) -> Unit){
            itemView.apply {
                val sharedPreferences = context.getSharedPreferences("weather_app", Context.MODE_PRIVATE)
                var temp = city.main.temp
                if (sharedPreferences.getUnits() == Units.IMPERIAL)
                    temp = 5.0/9 * (city.main.temp - 32.0)

                val date = Date(city.dt * 1000L)

                val lon = city.coord.lon * 4 / 60
                val hour = LocalDateTime.now().hour - 1 + lon

                if(hour > 19 || hour < 5){
                    itemContainer.setBackgroundResource(R.drawable.group_7)
                    conditions_emoji.visibility = View.GONE

                } else if(city.clouds.all > 50) {
                    itemContainer.setBackgroundResource(R.drawable.cloudy)
                    if (temp >= 26.0) {
                        conditions_emoji.setImageResource(R.drawable.ic_group_3)
                    } else if(city.weather.firstOrNull()?.description.equals("rain") || city.weather.firstOrNull()?.description.equals("light rain")) {
                            conditions_emoji.setImageResource(R.drawable.ic_group_5)
                    }else {
                            conditions_emoji.setImageResource(R.drawable.ic_clouds)
                    }
                }else{
                        if (temp >= 26.0){
                            itemContainer.setBackgroundResource(R.drawable.sunny)
                            conditions_emoji.setImageResource(R.drawable.ic_orange_sun)
                        }else{
                            itemContainer.setBackgroundResource(R.drawable.blue_sky)
                            conditions_emoji.setImageResource(R.drawable.ic_sun)
                        }
                    }

                val formatter = SimpleDateFormat("MMMM yyyy", Locale("en"))
                formatter.timeZone = TimeZone.getTimeZone("GMT-4")
                val current = LocalDateTime.now()
                if(24 - current.hour - lon < 0)
                    tvDate.text = "${current.dayOfMonth + 1} ${formatter.format(date)}"
                else
                    tvDate.text = "${current.dayOfMonth} ${formatter.format(date)}"

                tvCity.text = city.name
                if(sharedPreferences.getUnits() == Units.METRIC)
                    tvTemperature.text = "${city.main.temp_max.toInt()}°C /${city.main.temp_min.toInt()}°C"
                else
                    tvTemperature.text = "${city.main.temp_max.toInt()} °F /${city.main.temp_min.toInt()} °F"

                setOnClickListener { listener(city) }

                setOnLongClickListener {
                    Toast.makeText(context, "deleting not implemented yet", Toast.LENGTH_SHORT).show()
                    return@setOnLongClickListener true
                }
            }
        }
    }

}