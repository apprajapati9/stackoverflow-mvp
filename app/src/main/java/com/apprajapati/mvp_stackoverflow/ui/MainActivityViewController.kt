package com.apprajapati.mvp_stackoverflow.ui;

import android.app.Activity
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apprajapati.mvp_stackoverflow.R
import com.apprajapati.mvp_stackoverflow.repository.network.DataRepository
import com.apprajapati.mvp_stackoverflow.repository.network.DataRepositoryImpl
import com.apprajapati.mvp_stackoverflow.repository.network.models.Question
import com.apprajapati.mvp_stackoverflow.view.presenter.MainActivityPresenterImpl
import com.apprajapati.mvp_stackoverflow.view.presenter.MainActivityView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewController(private val activity: Activity) :
    MainActivityView {

    private lateinit var view: View
    private lateinit var questionRecyclerView: RecyclerView
    private lateinit var button: Button

    private lateinit var mainActivityPresenter: MainActivityPresenterImpl
    private var controller: DataRepository = DataRepositoryImpl()

    fun initViews() {

        activity.setContentView(R.layout.main_activity)

        view = activity.findViewById(R.id.rootView)

        mainActivityPresenter = MainActivityPresenterImpl(this, controller)

        questionRecyclerView = view.findViewById(R.id.recyclerview_questions)
        button = view.findViewById(R.id.requestButton)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                mainActivityPresenter.getQuestions()
            }
        }
    }

    override fun displayQuestions(list: List<Question>) {
        println("ajay Got questions here- > $list")

        showRecyclerView(true)
        questionRecyclerView.layoutManager = LinearLayoutManager(activity)
        questionRecyclerView.adapter =
            StackQuestionsAdapter(list, object : StackQuestionsAdapter.onClickListener {
                override fun OnQuestionClick(id: Int) {
                    showSnackbar("Question clicked.. $id")
                }
            })
    }

    fun showSnackbar(message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun displayNoQuestionsFound() {
        showSnackbar("No questions found!")
        showRecyclerView(false)
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


}
