package com.example.weatheracc.ui.main.Fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.weatheracc.adapters.CitiesAdapter
import com.example.weatheracc.models.Units
import com.example.weatheracc.viewModels.ForecastListViewModel
import com.example.weatheracc.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.forecast_list_fragment.view.*
import java.util.*
import javax.inject.Inject


class ForecastListFragment : DaggerFragment(){

    @Inject
    lateinit var factory : ViewModelProvider.Factory

    private val viewModel by viewModels<ForecastListViewModel> { factory }

    private val citiesAdapter by lazy {
        CitiesAdapter{

            findNavController().navigate(ForecastListFragmentDirections.actionForecastListFragmentToDetailScreen(it))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.forecast_list_fragment, container, false).apply {

            txtSwitcher.setOnClickListener { viewModel.updateUnits() }

            Rw.adapter = citiesAdapter
            floatingActionButton.setOnClickListener {
                findNavController().navigate(ForecastListFragmentDirections.actionForecastListFragmentToCitySearchFragment())

            }


            with(viewModel) {

                weatherList.observe(viewLifecycleOwner, Observer {
                    if(Optional.of(it.size).orElse(0) == 0){
                        backgroundImage.visibility = View.VISIBLE
                        girl_empty.visibility = View.VISIBLE
                        click_to_add.visibility = View.VISIBLE
                    }else{
                        backgroundImage.visibility = View.GONE
                        girl_empty.visibility = View.GONE
                        click_to_add.visibility = View.GONE
                    }
                    citiesAdapter.submitList(it)
                })

                units.observe(viewLifecycleOwner, Observer {
                    if(it == Units.METRIC) {
                        Cel.setTypeface(null, Typeface.BOLD)
                        Far.setTypeface(null, Typeface.NORMAL)
                    }else{
                        Far.setTypeface(null, Typeface.BOLD)
                        Cel.setTypeface(null, Typeface.NORMAL)
                    }

                })
            }
    }
    }
}
