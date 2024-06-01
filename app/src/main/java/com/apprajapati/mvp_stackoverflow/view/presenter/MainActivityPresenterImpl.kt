package com.apprajapati.mvp_stackoverflow.view.presenter

import com.apprajapati.mvp_stackoverflow.repository.network.DataRepository
import com.apprajapati.mvp_stackoverflow.view.MainActivityView

class MainActivityPresenterImpl(val view: MainActivityView, val dataController: DataRepository) :
    MainActivityPresenter {

    override suspend fun getQuestions() {
        val list = dataController.getQuestions()
        if (list.isEmpty()) {
            view.displayNoQuestionsFound()
        } else {
            view.displayQuestions(list)
        }
    }

}