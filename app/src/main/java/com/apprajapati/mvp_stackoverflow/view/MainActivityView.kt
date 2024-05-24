package com.apprajapati.mvp_stackoverflow.view

import com.apprajapati.mvp_stackoverflow.repository.network.models.Question

interface MainActivityView {
    fun displayQuestions(list: List<Question>)
    fun displayNoQuestionsFound()
}