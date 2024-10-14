package com.apprajapati.mvp_stackoverflow.util

import android.content.Context
import android.view.View
import android.widget.Toast


fun Context?.longToast(message: String){
    Toast.makeText(this, "message", Toast.LENGTH_LONG).show()
}

fun View.setGone() {
    this.visibility = View.GONE
}