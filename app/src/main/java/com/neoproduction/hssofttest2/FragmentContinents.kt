package com.neoproduction.hssofttest2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neoproduction.hssofttest2.utils.getViewModelOfActivity
import com.neoproduction.hssofttest2.utils.observe

class FragmentContinents : Fragment() {
    private lateinit var vm: MainVM
    private lateinit var rv: RecyclerView
    private lateinit var pbLoading: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main_continents, null)
        rv = v.findViewById(R.id.rvContinents)
        pbLoading = v.findViewById(R.id.pbLoading)
        rv.layoutManager = LinearLayoutManager(context)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm = getViewModelOfActivity {
            observe(notifyUpdateContinentsLD) {
                rv.adapter?.notifyDataSetChanged()
            }
            observe(progressBarContinentsVisibilityLD) { visible ->
                pbLoading.visibility = if (visible) View.VISIBLE else View.GONE
            }
            observe(listContinentsVisibilityLD) { visible ->
                rv.visibility = if (visible) View.VISIBLE else View.GONE
            }
        }

        rv.adapter = AdapterContinent(vm.continents, vm::onContinentSelected)
    }
}