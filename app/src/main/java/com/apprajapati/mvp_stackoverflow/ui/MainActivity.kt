package com.apprajapati.mvp_stackoverflow.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apprajapati.mvp_stackoverflow.R
import com.apprajapati.mvp_stackoverflow.presenter.MainActivityPresenterImpl
import com.apprajapati.mvp_stackoverflow.repository.network.DataRepository
import com.apprajapati.mvp_stackoverflow.repository.network.DataRepositoryImpl
import com.apprajapati.mvp_stackoverflow.repository.network.models.Question
import com.apprajapati.mvp_stackoverflow.view.MainActivityView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MainActivityView {
    lateinit var mPresenter : MainActivityPresenterImpl
    var controller: DataRepository = DataRepositoryImpl()

    lateinit var questionRecyclerView: RecyclerView
    lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        questionRecyclerView = findViewById(R.id.recyclerview_questions) as RecyclerView
        button = findViewById(R.id.requestButton)

        setUpPresenter()

        button.setOnClickListener{
            lifecycleScope.launch {
                Log.d("ajay mainOnclick", Thread.currentThread().name)
                mPresenter.getQuestions()
            }
        }

    }

    private fun setUpPresenter() {
        mPresenter = MainActivityPresenterImpl(this, controller)
    }

    override fun displayQuestions(list : List<Question>) {
        showRecyclerView(true)
        questionRecyclerView.layoutManager = LinearLayoutManager(this)
        questionRecyclerView.adapter = StackQuestionsAdapter(list, object : StackQuestionsAdapter.onClickListener {
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

    fun showRecyclerView(show: Boolean){
        if(show){
            questionRecyclerView.visibility = View.VISIBLE
            button.visibility = View.GONE
        }else{
            questionRecyclerView.visibility = View.GONE
            button.visibility = View.VISIBLE
        }
    }

    fun showSnackbar(message: String){
        val view = findViewById<ConstraintLayout>(R.id.rootView)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}