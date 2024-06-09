package com.apprajapati.mvp_stackoverflow.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.apprajapati.mvp_stackoverflow.view.presenter.MainActivityView

class MainActivity : BaseActivity(), MainActivityView.Listeners {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var mViewController: MainActivityViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewController = MainActivityViewController(LayoutInflater.from(this), null)
        mViewController.registerListener(this)
        setContentView(mViewController.getRootView())
    }


    override fun isNetworkAvailable(isIt: Boolean) {
        super.isNetworkAvailable(isIt)

        /*
            Interesting to note here that this method runs on ConnectivityThread, because listener is triggered in onActive and onLost method of connectivity manager inside the class.
         */
        Log.d(TAG, "Thread name-> ${Thread.currentThread().name}")
        /*
        This status should be tracked from viewmodel or stateflow to observe and act
         but this is for demonstration purposes only that you can have base activity and listener to listen to network state changes.
         */
        // if (!isIt) showSnackbar("Internet Off")
        runOnUiThread {
            Log.d(TAG, "Thread name-> ${Thread.currentThread().name}")
            //button.isEnabled = isNetworkAvailable
        }
    }

    override fun onDestroy() {
        mViewController.unRegisterListener(this)
        super.onDestroy()
    }

    override fun onQuestionClicked(id: Int) {
        Log.d(TAG, "onQuestionClicked: $id")
    }
}