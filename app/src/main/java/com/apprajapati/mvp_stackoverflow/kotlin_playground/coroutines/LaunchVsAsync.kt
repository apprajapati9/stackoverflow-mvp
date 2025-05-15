package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

/*
    Diff between async and launch

 */

suspend fun consumingTask(): Unit {
    delay(1000)
    println("Done Task1")
}

suspend fun consumingTask2(): Unit {
    delay(2000)
    println("Done Task2")

}

suspend fun consumingTask3(): Unit {
    delay(3000)
    println("Done Task3")

}

fun main(): Unit = runBlocking {

    println("Using Launch one after another ")
    val time1 = measureTimeMillis {
        launch {
            consumingTask()
            consumingTask2()
            consumingTask3()
        }.join()
    }

    println("Took $time1 for launch all.")

    println("=====")
    println("Using launch and joinAll for parallelism")

    val time2 = measureTimeMillis {
        val job1 = launch {
            consumingTask()
        }
        val job2 = launch {
            consumingTask2()

        }
        val job3 = launch {
            consumingTask3()
        }

        joinAll(job1, job2, job3)
    }

    println("Took $time2 for joinAll")

    println("=====")
    println("Using async await for parallelism")

    val time = measureTimeMillis {

        val handler = CoroutineExceptionHandler { _, e ->
            println("Caught Exception: $e")
        }

        val job: Job = launch(handler) {
            val one = async {
                try {
                    withTimeout(100) {
                        consumingTask()
                    }
                } catch (e: TimeoutCancellationException) {
                    println("Caught inside ${e.printStackTrace()}")
                }
            }
            val two = async { consumingTask2() }
            val three = async { consumingTask3() }

            val r = awaitAll(one, two, three)
            println("result -> $r")
        }
    }

    println("Took $time for this operation")


    println("Done - Remember launch is fire and forget, Async for getting result back")
}