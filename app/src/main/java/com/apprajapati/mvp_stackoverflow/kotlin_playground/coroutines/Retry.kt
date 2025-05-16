package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
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

suspend fun getRandomNumberAdv(delay: Long, expected: Int): Int {
    delay(delay)
    val number = Random.nextInt(1, 3000)
    if (number <= expected) {
        println("Exception Adv: $number")
        throw CancellationException("Cancelling coroutine!")
    }
    return number
}

suspend fun <T> retryAdvanced(
    times: Int,
    delay: Long = 0,
    retryOn: (Throwable) -> Boolean = { true },
    block: suspend () -> T
): T {

    var lastException: Throwable? = null

    repeat(times) {
        try {
            return block()
        } catch (e: Throwable) {
            if (!retryOn(e)) throw e //If false then throw it and not retry
            lastException = e
            if (delay > 0)
                delay(delay)
        }
    }

    throw lastException ?: IllegalStateException("Retry failed with unknown exceptions")
}

fun main(): Unit = runBlocking {

    supervisorScope {
        val j1 = launch {
            retry(3) {
                val ajay = getRandomNumber(delay = 1000, expected = 3000)
                println(ajay)
            }
        }

        val j2 = launch {
            retryAdvanced(times = 3, retryOn = { it is CancellationException }) {
                val ajay = getRandomNumberAdv(delay = 1000, expected = 3000)
                println(ajay)
            }
        }
    }

    //println("Final value $number")
}