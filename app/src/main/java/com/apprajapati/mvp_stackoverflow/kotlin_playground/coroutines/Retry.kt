package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

suspend fun <T> retry(attempts: Int, block: suspend () -> T): T {
    var lastException: Throwable? = null

    repeat(attempts) {
        try {
            return block.invoke()
        } catch (e: Throwable) {
            lastException = e
        }
    }

    throw lastException ?: IllegalStateException("Retry failed with unknown exceptions")
}

suspend fun getRandomNumber(delay: Long, expected: Int): Int {
    delay(delay)
    val number = Random.nextInt(1, 3000)
    if (number <= expected) {
        println("Exception : $number")
        throw CancellationException("Cancelling coroutine!")
    }
    return number
}

fun main(): Unit = runBlocking {

    retry(3) {
        val ajay = getRandomNumber(delay = 1000, expected = 2900)
        println(ajay)
    }
}