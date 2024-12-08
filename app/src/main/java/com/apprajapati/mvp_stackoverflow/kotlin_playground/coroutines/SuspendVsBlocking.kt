package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

suspend fun timeTakingTask() {
    withContext(Dispatchers.IO) {
        delay(5000)
    }
}

//This is blocking the thread because Task 1 will wait until its done before moving on to TASK2.
fun blockingFunction() {
    println("BLOCKING FUNCTION... ")
    runBlocking {
        println("Task 1 started")
        timeTakingTask()
        println("Task 1 done.")
    }

    runBlocking {
        println("Task 2 started")
        timeTakingTask()
        println("Task 2 done.")
    }
}

fun suspendFunction() {
    //The idea here is that timeTakingTask is done in IO thread so because this coroutine is suspendable, it will be free to do other work which is why, Task 1 and Task2 both will be started. So we can see that thread was not blocked and moved on to next Task 2 while background task was happening.

    println("SUSPEND FUNCTION... ")
    CoroutineScope(dispatcher).launch {
        println("Task 1 started")
        timeTakingTask()
        println("Task 1 done.")
    }

    CoroutineScope(dispatcher).launch {
        println("Task 2 started")
        timeTakingTask()
        println("Task 2 done.")
    }

    /*
        The reason why main doesn't finish after this function

        The issue is that CoroutineScope(dispatcher) creates a scope that is not explicitly tied to the lifecycle of the main function. This means the scope persists and keeps the program alive even after all launched coroutines complete. The program does not terminate until the CoroutineScope itself is explicitly cancelled or garbage-collected.

        The fix is using cancelling scope manually.
     */
}

fun suspendFunctionWithTermination() = runBlocking {
    println("SUSPEND FUNCTION... ")
    launch {
        println("Task 1 started")
        timeTakingTask()
        println("Task 1 done.")
    }

    launch {
        println("Task 2 started")
        timeTakingTask()
        println("Task 2 done.")
    }
}

fun main() {

    //suspendFunction()
    suspendFunctionWithTermination()
    //blockingFunction()
}