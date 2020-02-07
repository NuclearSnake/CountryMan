package com.neoproduction.hssofttest2.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(): T =
    ViewModelProvider(this)[T::class.java]
inline fun <reified T : ViewModel> Fragment.getViewModelOfActivity(): T =
    ViewModelProvider(this.activity ?: throw Exception("Invalid Activity"))[T::class.java]
inline fun <reified T : ViewModel> Fragment.getViewModelOfActivity(body: T.() -> Unit): T {
    val vm = ViewModelProvider(this.activity ?: throw Exception("Invalid Activity"))[T::class.java]
    vm.body()
    return vm
}
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, Observer(body))
