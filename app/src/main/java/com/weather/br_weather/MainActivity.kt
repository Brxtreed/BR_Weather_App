package com.weather.br_weather

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        var isConnectionDropped = false
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if(isConnectionDropped) {
                showConnectivitySuccess()
                isConnectionDropped = false
            }
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }

        // lost network connection
        override fun onLost(network: Network) {
            isConnectionDropped = true
            showConnectivityError()


            super.onLost(network)

        }
    }
    private lateinit var viewModel: HomeViewModel
    @Inject lateinit var networkRequest: NetworkRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
        initObservers()

    }

    private fun showConnectivityError() {
        runOnUiThread {
            Toast.makeText(this, "Disconnection Error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showConnectivitySuccess() {
        runOnUiThread {
            Toast.makeText(this, "Connection has been re-established", Toast.LENGTH_SHORT).show()
        }

    }


    private fun initObservers(){

        }



}