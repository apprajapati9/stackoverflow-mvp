package com.apprajapati.mvp_stackoverflow.screens.base_view

import android.content.Context
import android.view.View

abstract class BaseViewController : BaseViewContract {

    lateinit var mRootView: View

    override fun getRootView(): View {
        return mRootView
    }

    protected fun <T> findView(viewId: Int): T {
        return getRootView().findViewById(viewId)
    }

    protected fun getContext(): Context {
        return getRootView().context
    }

}