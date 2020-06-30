package com.example.weatheracc.ui.main.Fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.adapters.AutocompleteAdapter
import com.example.weatheracc.adapters.CitiesSearchAdapter
import com.example.weatheracc.viewModels.CitySearchViewModel
import com.example.weatheracc.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.city_search_fragment.view.*
import javax.inject.Inject

class CitySearchFragment : DaggerFragment() {

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private val viewModel by viewModels<CitySearchViewModel> { factory }


    private val savedCityListAdapter by lazy { CitiesSearchAdapter{
        if (it.name.equals("Your current location")) {
            it.name = viewModel.currentName
            it.sys.country = viewModel.currentCountry
        }
        viewModel.storeCity(it)
        findNavController().popBackStack()
    }
    }

    private val searchListAdapter by lazy { CitiesSearchAdapter{
        Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        viewModel.storeCity(it)
        findNavController().popBackStack()
    }
    }

    private val autocompleteAdapter by lazy {
        AutocompleteAdapter{
            viewModel.searchCity(it.description)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.city_search_fragment, container, false).apply {

            checkWifi()
            checkPermission()

            Search.addTextChangedListener { text ->
                if(text?.length!! >= 3){
                    viewModel.searchCityName(Search.text.toString())
                }
            }

            Search.setOnEditorActionListener { v, actionId, event ->

                if (actionId == EditorInfo.IME_ACTION_DONE){

                    if (Search.text.toString().equals("") || Search.text.toString().equals("\n")){
                        Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show()
                    }else {
                        viewModel.searchCity(Search.text.toString())
                    }
                    return@setOnEditorActionListener true
                }
                false
            }

            rvCity.adapter = searchListAdapter

            back.setOnClickListener {
                findNavController().navigate(CitySearchFragmentDirections.actionCitySearchFragmentToForecastListFragment2())
            }

            with(viewModel){
                cityList.observe(viewLifecycleOwner, Observer {
                    if (it.isNotEmpty()){
                        searchListAdapter.submitList(it)
                        handleVisibility(textView, rvCity, false)
                    }else{
                        textView.text = "List is empty"
                        handleVisibility(textView, rvCity, true)
                    }
                    rvCity.adapter = searchListAdapter
                })
                errorMessage.observe(viewLifecycleOwner, Observer {
                    textView.text = it
                    handleVisibility(textView, rvCity, true)
                })
                savedCityList.observe(viewLifecycleOwner, Observer {
                    savedCityListAdapter.submitList(it)
                    rvCity.adapter = savedCityListAdapter
                })
                citiesName.observe(viewLifecycleOwner, Observer {
                    autocompleteAdapter.submitList(it)
                    rvCity.adapter = autocompleteAdapter
                })
            }
        }
    }

    private fun checkWifi() {
        var permissionCheck =
            ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.INTERNET)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission(){

        var permissionCheck = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION)

        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PackageManager.PERMISSION_GRANTED
            )
            checkPermission()
        }
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val locationManager : LocationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if(location != null){
                val lat = location.latitude
                val lon = location.longitude
                viewModel.findCurrentCity(lat, lon)
            }


        }
    }

    private fun handleVisibility(textView: View, recyclerView: RecyclerView, shouldShowError: Boolean){
        textView.visibility = if(shouldShowError) View.VISIBLE else View.GONE
        recyclerView.visibility = if (shouldShowError) View.GONE else View.VISIBLE
    }

}
