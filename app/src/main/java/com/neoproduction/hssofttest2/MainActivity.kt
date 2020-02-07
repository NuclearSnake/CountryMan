package com.neoproduction.hssofttest2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neoproduction.hssofttest2.utils.getViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var vm: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = getViewModel()
        vm.onActivityCreated()
    }
}