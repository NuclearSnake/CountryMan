package com.neoproduction.hssofttest2.data

interface DataProvider {
    fun getContinents(
        onFailure: (e: Throwable) -> Unit,
        onSuccess: (countries: List<Continent>) -> Unit
    )

    fun getCountries(
        continentId: Int,
        onFailure: (e: Throwable) -> Unit,
        onSuccess: (countries: List<Country>) -> Unit
    )
}