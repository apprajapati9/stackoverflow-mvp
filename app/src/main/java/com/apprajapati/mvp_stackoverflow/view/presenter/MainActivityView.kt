package com.apprajapati.mvp_stackoverflow.view.presenter

import com.apprajapati.mvp_stackoverflow.repository.network.models.Question
import com.apprajapati.mvp_stackoverflow.ui.BaseObservableView

interface MainActivityView : BaseObservableView<MainActivityView.Listeners> {

    interface Listeners {
        fun onQuestionClicked(id: Int)
    }

    fun displayQuestions(list: List<Question>)
    fun displayNoQuestionsFound()
}