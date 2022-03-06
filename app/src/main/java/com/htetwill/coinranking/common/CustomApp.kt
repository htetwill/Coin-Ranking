package com.htetwill.coinranking.common

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.htetwill.coinranking.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomApp : Application(), HasAndroidInjector{
    var isNetworkAvailable = false
        private set

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        listenForNetworkConnection()
        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    private fun listenForNetworkConnection() {
        try {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isNetworkAvailable = true
                }

                override fun onLost(network: Network) {
                    isNetworkAvailable= false
                }
            })

        } catch (e: Exception) {
            isNetworkAvailable = true
        }
    }
}