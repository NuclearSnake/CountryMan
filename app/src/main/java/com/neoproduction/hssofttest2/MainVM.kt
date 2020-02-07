package com.neoproduction.hssofttest2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neoproduction.hssofttest2.data.Continent
import com.neoproduction.hssofttest2.data.Country
import com.neoproduction.hssofttest2.data.DataProvider
import com.neoproduction.hssofttest2.data.GraphQLDataProvider
import timber.log.Timber

class MainVM : ViewModel() {
    val dataProvider: DataProvider = GraphQLDataProvider()

    val countries = mutableListOf<Country>()
    val continents = mutableListOf<Continent>()

    // Fragment Continents
    private val ldNotifyUpdateContinents = MutableLiveData<Boolean>()
    val notifyUpdateContinentsLD: LiveData<Boolean>
        get() = ldNotifyUpdateContinents
    private val ldProgressBarContinentsVisibility = MutableLiveData<Boolean>(false)
    val progressBarContinentsVisibilityLD: LiveData<Boolean>
        get() = ldProgressBarContinentsVisibility
    private val ldListContinentsVisibility = MutableLiveData<Boolean>(false)
    val listContinentsVisibilityLD: LiveData<Boolean>
        get() = ldListContinentsVisibility

    // Fragment Countries
    private val ldNotifyUpdateCountries = MutableLiveData<Boolean>()
    val notifyUpdateCountriesLD: LiveData<Boolean>
        get() = ldNotifyUpdateCountries
    private val ldProgressBarCountriesVisibility = MutableLiveData<Boolean>(false)
    val progressBarCountriesVisibilityLD: LiveData<Boolean>
        get() = ldProgressBarCountriesVisibility
    private val ldHintVisibility = MutableLiveData<Boolean>(true)
    val hintVisibilityLD: LiveData<Boolean>
        get() = ldHintVisibility
    private val ldListCountriesVisibility = MutableLiveData<Boolean>(false)
    val listCountriesVisibilityLD: LiveData<Boolean>
        get() = ldListCountriesVisibility

    fun onActivityCreated() {
        loadContinents()
    }

    private fun loadContinents() {
        if(continents.isNotEmpty()){
            onContinentsLoaded()
            return
        }

        ldProgressBarContinentsVisibility.postValue(true)
        dataProvider.getContinents(
            onFailure = { Timber.e(it) },
            onSuccess = {
                Timber.d("Continents loaded")
                continents.clear()
                continents.addAll(it)
                onContinentsLoaded()
            })
    }

    fun onContinentsLoaded(){
        ldNotifyUpdateContinents.postValue(true)
        ldProgressBarContinentsVisibility.postValue(false)
        ldListContinentsVisibility.postValue(true)
    }

    fun onContinentSelected(position: Int) {
        loadCountries(position)
    }

    private fun loadCountries(position: Int) {
        ldHintVisibility.postValue(false)
//        ldProgressBarCountriesVisibility.postValue(true)
        dataProvider.getCountries(position,
            onFailure = { Timber.e(it) },
            onSuccess = {
                Timber.d("Countries loaded")
                countries.clear()
                countries.addAll(it)
                ldListCountriesVisibility.postValue(true)
//                ldProgressBarCountriesVisibility.postValue(false)
                ldNotifyUpdateCountries.postValue(true)
            })
    }
}