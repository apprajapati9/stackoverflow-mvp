package com.apprajapati.mvp_stackoverflow.base_view

import com.apprajapati.mvp_stackoverflow.ui.BaseObservableView
import java.util.Collections

abstract class BaseObservableView<ListenerType> : BaseViewController(),
    BaseObservableView<ListenerType> {

    //Observable pattern listeners
    private val mListeners = mutableSetOf<ListenerType>()

    override fun registerListener(listener: ListenerType) {
        mListeners.add(listener)
    }

    override fun unRegisterListener(listener: ListenerType) {
        mListeners.remove(listener)
    }

    fun getListeners(): Set<ListenerType> {
        return Collections.unmodifiableSet(mListeners)
    }
}
