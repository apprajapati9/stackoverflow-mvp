package com.apprajapati.mvp_stackoverflow.screens.base_view

interface BaseObservableView<T> {

    fun registerListener(listener: T)

    fun unRegisterListener(listener: T)
}