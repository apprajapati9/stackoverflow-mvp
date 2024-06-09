package com.apprajapati.mvp_stackoverflow.ui

interface BaseObservableView<T> {

    fun registerListener(listener: T)

    fun unRegisterListener(listener: T)
}