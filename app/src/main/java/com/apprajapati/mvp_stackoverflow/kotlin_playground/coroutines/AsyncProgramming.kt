package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

fun fetchData(): String {
    // Simulating a time-consuming operation, but in a non-blocking way
    Thread.sleep(2000)  // Simulate network delay
    return "Data fetched"
}

fun emitData() = flow<Int> {
    for(i in 1..100){
        emit(i)
    }
}

fun main() = runBlocking {
    println("Fetching data...")

    // Launching an asynchronous task
    val deferred = async {
        fetchData()  // This runs asynchronously
    }

    println("Doing other things while waiting for data...")
    val list = (1..100).toList()

    for(i in list){
        delay(100)
        println("$i,")
    }

    // Awaiting the result (this is a suspend function, so it doesn't block the main thread)
    val result = deferred.await()

    println(result)
}