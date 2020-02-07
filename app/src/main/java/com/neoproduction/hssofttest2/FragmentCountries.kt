package com.neoproduction.hssofttest2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neoproduction.hssofttest2.utils.getViewModelOfActivity
import com.neoproduction.hssofttest2.utils.observe

class FragmentCountries : Fragment() {
    private lateinit var vm: MainVM
    private lateinit var rv: RecyclerView
    private lateinit var pbLoading: ProgressBar
    private lateinit var tvHint: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main_countries, null)
        rv = v.findViewById(R.id.rvCountries)
        pbLoading = v.findViewById(R.id.pbLoading)
        tvHint = v.findViewById(R.id.tvHint)
        rv.layoutManager = LinearLayoutManager(context)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = getViewModelOfActivity {
            observe(notifyUpdateCountriesLD) {
                rv.smoothScrollToPosition(0)
                rv.adapter?.notifyDataSetChanged()
            }
            observe(hintVisibilityLD) { visible ->
                tvHint.visibility = if (visible) View.VISIBLE else View.GONE
            }
            observe(listCountriesVisibilityLD) { visible ->
                rv.visibility = if (visible) View.VISIBLE else View.INVISIBLE
            }
            observe(progressBarCountriesVisibilityLD) { visible ->
                pbLoading.visibility = if (visible) View.VISIBLE else View.GONE
            }
        }
        rv.adapter = AdapterCountries(vm.countries)
    }
}