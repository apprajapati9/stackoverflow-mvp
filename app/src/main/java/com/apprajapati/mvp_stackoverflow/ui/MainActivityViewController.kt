package com.apprajapati.mvp_stackoverflow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apprajapati.mvp_stackoverflow.R
import com.apprajapati.mvp_stackoverflow.base_view.BaseViewController
import com.apprajapati.mvp_stackoverflow.repository.network.DataRepository
import com.apprajapati.mvp_stackoverflow.repository.network.DataRepositoryImpl
import com.apprajapati.mvp_stackoverflow.repository.network.models.Question
import com.apprajapati.mvp_stackoverflow.view.presenter.MainActivityPresenterImpl
import com.apprajapati.mvp_stackoverflow.view.presenter.MainActivityView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewController(
    inflater: LayoutInflater, parent: ViewGroup?
) : BaseViewController(), MainActivityView {

    private lateinit var questionRecyclerView: RecyclerView
    private lateinit var button: Button

    private lateinit var mainActivityPresenter: MainActivityPresenterImpl
    private var controller: DataRepository = DataRepositoryImpl()


    //Observable pattern listeners
    private val listeners = arrayListOf<MainActivityView.Listeners>()

    init {
        mRootView = inflater.inflate(R.layout.main_activity, parent, false)
        initViews()
    }


    private fun initViews() {

        mainActivityPresenter = MainActivityPresenterImpl(this, controller)

        questionRecyclerView = findView(R.id.recyclerview_questions)
        button = findView(R.id.requestButton)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                mainActivityPresenter.getQuestions()
            }
        }
    }

    override fun registerListener(listener: MainActivityView.Listeners) {
        listeners.add(listener)
    }

    override fun unRegisterListener(listener: MainActivityView.Listeners) {
        listeners.remove(listener)
    }

    override fun displayQuestions(list: List<Question>) {
        println("ajay Got questions here- > $list")

        showRecyclerView(true)
        questionRecyclerView.layoutManager = LinearLayoutManager(getContext())
        questionRecyclerView.adapter =
            StackQuestionsAdapter(list, object : StackQuestionsAdapter.onClickListener {
                override fun OnQuestionClick(id: Int) {
                    showSnackBar("Question clicked.. $id")

                    //Let activity handle such events like moving to another screen being a controller. This way passing adapt listener to activity so it can know what to do.
                    listeners.forEach {
                        it.onQuestionClicked(id)
                    }
                }

            })
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(getRootView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun displayNoQuestionsFound() {
        showSnackBar("No questions found!")
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
