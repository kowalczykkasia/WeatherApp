package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.DetailDailyInfo


class DetailDailyInfoAdapter(private val list: List<DetailDailyInfo>) : RecyclerView.Adapter<DetailDailyInfoAdapter.DetailDailyInfoViewHolder>(){


    class DetailDailyInfoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView : TextView
        val dataTextView : TextView
        init {
         nameTextView = itemView.findViewById(R.id.Dinfo)
         dataTextView = itemView.findViewById(R.id.Ddata)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailDailyInfoViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val infoView = inflater.inflate(R.layout.daily_info, parent,false)
        return DetailDailyInfoViewHolder(infoView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DetailDailyInfoViewHolder, position: Int) {
       val detailInfo : DetailDailyInfo = list.get(position)
        val name = holder.nameTextView
        name.text = detailInfo.Name
        val data = holder.dataTextView
        data.text = detailInfo.Data

    }
}