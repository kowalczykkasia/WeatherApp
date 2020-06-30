package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.WeatherForecast
import kotlinx.android.synthetic.main.item_search_city.view.*
import java.util.*

class CitiesSearchAdapter (
    private val listener : (WeatherForecast) -> Unit
) : ListAdapter<WeatherForecast, CitiesSearchAdapter.CitiesSearchViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitiesSearchViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_city, parent,false)
        )

    override fun onBindViewHolder(holder: CitiesSearchViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherForecast>(){
            override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast
            )=
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast
            )=
                oldItem == newItem
        }
    }

    class CitiesSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(city: WeatherForecast, listener: (WeatherForecast) -> Unit) {
            itemView.apply {
                if(city.name.equals("Your current location"))
                    clock.setImageResource(R.drawable.ic_near_me)
                else
                    clock.setImageResource(R.drawable.ic_schedule)
                val locale = Locale("", city.sys.country)
                countryName.text = locale.getDisplayCountry()
                cityName.text = city.name + " "
                setOnClickListener { listener(city) }
            }
        }

    }


}