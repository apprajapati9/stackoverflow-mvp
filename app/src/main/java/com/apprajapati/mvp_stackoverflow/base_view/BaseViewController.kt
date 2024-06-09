package com.apprajapati.mvp_stackoverflow.base_view

import android.content.Context
import android.view.View
import com.apprajapati.mvp_stackoverflow.ui.BaseViewContract

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