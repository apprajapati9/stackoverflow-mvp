package com.apprajapati.mvp_stackoverflow.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apprajapati.mvp_stackoverflow.util.InternetConnectionManager
import com.apprajapati.mvp_stackoverflow.util.NetworkStatus

abstract class BaseActivity : AppCompatActivity(), NetworkStatus {

    private lateinit var connection: InternetConnectionManager

    var isNetworkAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        setUpNetworkState()
    }

    private fun setUpNetworkState() {
        connection = InternetConnectionManager(this)
        connection.setNetworkListener(this)
    }

    override fun isNetworkAvailable(isIt: Boolean) {
        isNetworkAvailable = isIt
    }
}