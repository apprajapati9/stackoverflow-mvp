package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun asyncShowcase() = runBlocking {
    println("Async showcase...")

    val user1 = async {
        println("Start user1")
        delay(1000)
        println("Done user1")
    }
    val user2 = async {
        println("Start user2")
        delay(1000)
        println("Done user2")

    }
    val user3 = async {
        println("Start user3")
        delay(1000)
        println("Done user3")
    }

    awaitAll(user1, user2, user3)
}

//TODO : another example for clarity is needed.
fun launchShowcase() = runBlocking {

    println("Launch showcase...")

    val job = launch {
        println("Start user1")
        delay(1000)
        println("Done user1")
    }
    val job1 = launch {
        println("Start user2")
        delay(1000)
        println("Done user2")

    }
    val job2 = launch {
        println("Start user3")
        delay(1000)
        println("Done user3")
    }

}

fun main() {
    asyncShowcase()

    launchShowcase()
}