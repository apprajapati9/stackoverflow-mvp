package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    launch {
        delay(500L)
        println("A")
    }

    coroutineScope {
        launch {
            delay(1000L)
            println("B")
        }

        delay(100L)
        println("C")
    }
    println("D")
}