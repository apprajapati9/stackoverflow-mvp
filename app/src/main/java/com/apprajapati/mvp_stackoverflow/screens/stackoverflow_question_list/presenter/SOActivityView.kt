package com.apprajapati.mvp_stackoverflow.screens.stackoverflow_question_list.presenter

import com.apprajapati.mvp_stackoverflow.repository.network.models.Question

interface SOActivityView {

    interface Listeners {
        fun onQuestionClicked(id: Int)
    }

    fun displayQuestions(list: List<Question>)
    fun displayNoQuestionsFound()
}