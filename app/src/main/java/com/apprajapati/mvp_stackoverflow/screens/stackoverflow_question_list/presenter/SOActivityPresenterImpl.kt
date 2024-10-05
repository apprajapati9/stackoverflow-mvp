package com.apprajapati.mvp_stackoverflow.screens.stackoverflow_question_list.presenter

import com.apprajapati.mvp_stackoverflow.repository.network.DataRepository

class SOActivityPresenterImpl(
    private val view: SOActivityView,
    private val dataController: DataRepository
) :
    SOActivityPresenter {

    override suspend fun getQuestions() {
        val list = dataController.getQuestions()
        if (list.isEmpty()) {
            view.displayNoQuestionsFound()
        } else {
            view.displayQuestions(list)
        }
    }

}