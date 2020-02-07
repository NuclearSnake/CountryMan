package com.neoproduction.hssofttest2.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.neoproduction.ContinentsQuery
import com.neoproduction.CountriesQuery
import timber.log.Timber

class GraphQLDataProvider : DataProvider {
    val apollo: ApolloClient by lazy {
        ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com/").build()
    }

    private val continents = mutableListOf<Continent>()
    private val countries = mutableListOf<Country>()

    override fun getContinents(
        onFailure: (e: Throwable) -> Unit,
        onSuccess: (countries: List<Continent>) -> Unit
    ) {
        if(continents.isNotEmpty()) {
            onSuccess(continents)
            return
        }

        val continentsQuery = ContinentsQuery.builder().build()

        apollo.query(continentsQuery).enqueue(object : ApolloCall.Callback<ContinentsQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Timber.e(e)
            }

            override fun onResponse(response: Response<ContinentsQuery.Data>) {
                val continentsLoaded = response.data()?.continents()
                if (continentsLoaded != null) {
                    val newContinents = continentsLoaded.mapNotNull { graphQLContinent ->
                        Continent(
                            graphQLContinent.code() ?: return@mapNotNull null,
                            graphQLContinent.name() ?: return@mapNotNull null
                        )
                    }
                    continents.clear()
                    continents.addAll(newContinents)

                    onSuccess(continents)
                }
            }
        })
    }

    override fun getCountries(
        continentId: Int,
        onFailure: (e: Throwable) -> Unit,
        onSuccess: (countries: List<Country>) -> Unit
    ) {
        val countriesQuery = CountriesQuery.builder()
            .codevalue(continents[continentId].code)
            .build()

        apollo.query(countriesQuery).enqueue(object : ApolloCall.Callback<CountriesQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                onFailure(e)
            }

            override fun onResponse(response: Response<CountriesQuery.Data>) {
                val countriesLoaded = response.data()?.continent()?.countries()
                if (countriesLoaded != null) {
                    val newCountries = countriesLoaded.mapNotNull { graphQLCountry ->
                        val languages = graphQLCountry.languages()?.mapNotNull languagesMap@{
                            return@languagesMap it.name()
                        } ?: return@mapNotNull null

                        Country(
                            graphQLCountry.code() ?: return@mapNotNull null,
                            graphQLCountry.name() ?: return@mapNotNull null,
                            languages,
                            graphQLCountry.currency(),
                            graphQLCountry.emoji(),
                            graphQLCountry.phone()
                        )
                    }

                    countries.clear()
                    countries.addAll(newCountries)

                    onSuccess(countries)
                }
            }
        })
    }
}