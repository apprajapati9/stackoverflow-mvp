package com.apprajapati.mvp_stackoverflow.ui

import android.view.View

interface MainActivityViewContract {

    interface Listeners {
        fun onQuestionClicked(id: Int)
    }

    fun registerListener(listener: Listeners)
    fun unRegisterListener(listener: Listeners)

    fun getRootView(): View

}