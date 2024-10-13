package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

suspend fun sequentialRequests(){
    val totalTime = measureTimeMillis {
        val r1 = delayFunction1()
        val r2 = delayFunction2()

        println("total ${r1+r2}")
    }

    println("done sequentially - totalTime-> $totalTime")
}

fun main() = runBlocking {

    //concurrent because of async
    val totalTime  = measureTimeMillis {
        val delay1  = async {
            delayFunction1()
        }
        val delay2  = async {
            delayFunction2()
        }
        val together = awaitAll(delay1, delay2)
        println("time -> ${together}}")
    }
    println("done concurrently - totalTime $totalTime")

    //without async await
    sequentialRequests()
}

suspend fun delayFunction2() : Long {
    delay(1000)
    return 1000
}

suspend fun delayFunction1() : Long {
    delay(500)
    return 500
}