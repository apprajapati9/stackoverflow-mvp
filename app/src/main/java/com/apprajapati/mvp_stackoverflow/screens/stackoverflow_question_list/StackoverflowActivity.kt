package com.apprajapati.mvp_stackoverflow.screens.stackoverflow_question_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.apprajapati.mvp_stackoverflow.screens.base_view.BaseActivity
import com.apprajapati.mvp_stackoverflow.screens.stackoverflow_question_list.presenter.SOActivityView
import com.apprajapati.mvp_stackoverflow.screens.stackoverflow_question_list.view.SOActivityViewController

class SOActivity : BaseActivity(), SOActivityView.Listeners {

    private val TAG = SOActivity::class.java.simpleName
    private lateinit var mViewController: SOActivityViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewController = SOActivityViewController(LayoutInflater.from(this), null)
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
            mViewController.isInternetAvailable(isIt)
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