package com.apprajapati.mvp_stackoverflow.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apprajapati.mvp_stackoverflow.R
import com.apprajapati.mvp_stackoverflow.repository.network.DataRepository
import com.apprajapati.mvp_stackoverflow.repository.network.DataRepositoryImpl
import com.apprajapati.mvp_stackoverflow.repository.network.models.Question
import com.apprajapati.mvp_stackoverflow.view.MainActivityView
import com.apprajapati.mvp_stackoverflow.view.presenter.MainActivityPresenterImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : BaseActivity(), MainActivityView {

    private val TAG = MainActivity::class.java.simpleName

    lateinit var mPresenter: MainActivityPresenterImpl
    var controller: DataRepository = DataRepositoryImpl()

    lateinit var questionRecyclerView: RecyclerView
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        questionRecyclerView = findViewById(R.id.recyclerview_questions) as RecyclerView
        button = findViewById(R.id.requestButton)

        setUpPresenter()


        button.isEnabled = isNetworkAvailable
        button.setOnClickListener {
            lifecycleScope.launch {
                Log.d("$TAG mainOnclick", Thread.currentThread().name)
                mPresenter.getQuestions()
            }
        }

    }

    private fun setUpPresenter() {
        mPresenter = MainActivityPresenterImpl(this, controller)
    }

    override fun displayQuestions(list: List<Question>) {
        showRecyclerView(true)
        questionRecyclerView.layoutManager = LinearLayoutManager(this)
        questionRecyclerView.adapter =
            StackQuestionsAdapter(list, object : StackQuestionsAdapter.onClickListener {
                override fun OnQuestionClick(id: Int) {
                    showSnackbar("Question clicked.. $id")
                }

            })

        showSnackbar("Got questions..")
    }

    override fun displayNoQuestionsFound() {
        showSnackbar("No questions found!")
        // button.setText()
    }

    private fun showRecyclerView(show: Boolean) {
        if (show) {
            questionRecyclerView.visibility = View.VISIBLE
            button.visibility = View.GONE
        } else {
            questionRecyclerView.visibility = View.GONE
            button.visibility = View.VISIBLE
        }
    }

    fun showSnackbar(message: String) {
        val view = findViewById<ConstraintLayout>(R.id.rootView)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
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
        if (!isIt) showSnackbar("Internet Off")
        runOnUiThread {
            Log.d(TAG, "Thread name-> ${Thread.currentThread().name}")
            button.isEnabled = isNetworkAvailable
        }
    }
}