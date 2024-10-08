package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun launchOnIO(block: suspend () -> Unit) {
    try {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            block()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun main() = runBlocking {
    println("ajay1st - thread name = ${Thread.currentThread()}")
    launchOnIO {
        delay(5000)
        println("thread name = ${Thread.currentThread()}")
        println("ajay")
    }
    Thread.sleep(6000)
}