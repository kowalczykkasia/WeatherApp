package com.example.weatheracc.adapters

import com.example.weatheracc.models.HourlyWeather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import java.text.SimpleDateFormat
import java.util.*


class HourlyWeatherAdapter(private val list: List<HourlyWeather>) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>(){


    class HourlyWeatherViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val hourTv : TextView
        val descriptionTv : TextView
        val temperatureTv : TextView
        val line : View
        init {
            line = itemView.findViewById(R.id.lineView)
            hourTv = itemView.findViewById(R.id.hour)
            descriptionTv = itemView.findViewById(R.id.description)
            temperatureTv = itemView.findViewById(R.id.Tvtemperature)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val infoView = inflater.inflate(R.layout.item_saved_daily, parent,false)
        return HourlyWeatherViewHolder(infoView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {

        val detailInfo : HourlyWeather = list.get(position)

        val date = Date(detailInfo.dt * 1000L)
        var formatter = SimpleDateFormat("HH:mm", Locale("en"))

        val hour = holder.hourTv
        hour.text = formatter.format(date)
        val desc = holder.descriptionTv
        desc.text = detailInfo.weather.firstOrNull()?.description
        val temp = holder.temperatureTv
        temp.text = "${detailInfo.main.temp.toInt()}°"

        formatter = SimpleDateFormat("H", Locale("en"))
        if(formatter.format(date).toInt() >= 23) {
            val lineView = holder.line
            lineView.visibility = View.GONE
        }

    }
}