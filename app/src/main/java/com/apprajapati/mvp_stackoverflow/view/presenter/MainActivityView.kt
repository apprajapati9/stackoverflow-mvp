package com.apprajapati.mvp_stackoverflow.view.presenter

import com.apprajapati.mvp_stackoverflow.repository.network.models.Question

interface MainActivityView {

    interface Listeners {
        fun onQuestionClicked(id: Int)
    }

    fun displayQuestions(list: List<Question>)
    fun displayNoQuestionsFound()
}