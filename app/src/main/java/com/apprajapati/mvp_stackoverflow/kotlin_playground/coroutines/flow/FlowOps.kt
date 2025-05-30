package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

suspend fun counter() = flow {
    val list = listOf(1, 2, 2, 3, 2, 3, 4, 4, 4, 4, 5, 4)
    for (i in list) {
        emit(i)
        delay(100)
    }
}

fun main(): Unit = runBlocking {

    counter().distinctUntilChanged().collect { item ->
        println(item)
    }

    /*
    The debounce operator is beneficial when dealing with user input or event streams where you want to wait for a brief period of inactivity before processing the latest data. It helps to avoid excessive computations and ensures that you are handling the most relevant and up-to-date information.
     */
}