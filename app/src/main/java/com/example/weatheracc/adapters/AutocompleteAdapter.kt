package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.models.AutocompleteCities
import com.example.weatheracc.R
import kotlinx.android.synthetic.main.item_search_city.view.*

class AutocompleteAdapter (
    private val listener : (AutocompleteCities) -> Unit
) : ListAdapter<AutocompleteCities, AutocompleteAdapter.AutocompleteCitiesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AutocompleteCitiesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_city, parent,false)
        )

    override fun onBindViewHolder(holder: AutocompleteCitiesViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AutocompleteCities>(){
            override fun areItemsTheSame(oldItem: AutocompleteCities, newItem: AutocompleteCities
            )=
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AutocompleteCities, newItem: AutocompleteCities
            )=
                oldItem == newItem
        }
    }

    class AutocompleteCitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(city : AutocompleteCities, listener: (AutocompleteCities) -> Unit){
            itemView.apply {

                cityName.text = "${city.structured_formatting.main_text} "
                countryName.text = city.structured_formatting.secondary_text
                clock.visibility = View.GONE

                setOnClickListener{
                    listener(city)
                }
            }
        }
    }

}