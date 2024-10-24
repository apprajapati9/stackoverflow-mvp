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

/*
Launch modes: 
The "standard" and "singleTop" modes differ from each other in just one respect:
Every time there's new intent for a "standard" activity, a new instance of the class is created
to respond to that intent. Each instance handles a single intent. Similarly,
a new instance of a "singleTop" activity may also be created to handle a new intent.
However, if the target task already has an existing instance of the activity at the top of its stack,
that instance will receive the new intent (in an onNewIntent() call); a new instance is not created.
In other circumstances — for example, if an existing instance of
the "singleTop" activity is in the target task, but not at the top of the stack,
or if it's at the top of a stack, but not in the target task — a new instance would be created
and pushed on the stack.
 */