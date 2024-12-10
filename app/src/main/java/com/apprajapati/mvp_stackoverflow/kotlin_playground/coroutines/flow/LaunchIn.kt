package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.EmptyCoroutineContext


fun <T> printWithTimePassed(value: T, startTime: Long) {
    val time = System.currentTimeMillis() - startTime
    println("${time}ms : $value")
}

suspend fun checkTime() {
    val currentTime = System.currentTimeMillis()

    println(Thread.currentThread().name)
    (1..5).forEach {
        delay(200)
        printWithTimePassed(it, currentTime)
    }

    listOf("5", "4", "3", "2", "1").forEach {
        delay(200)
        printWithTimePassed(it, currentTime)
    }
}

suspend fun flowBuilders() {
    //flowOf builder
    val firstFlow = flowOf(1, 2, 3)

    firstFlow.collect { num ->
        println("emitted $num")
    }

    //asFlow builder can be used on collections
    arrayListOf("11", "12", "23").asFlow().collect {
        println("emitted $it")
    }

    //Flow builder
    flow {
        delay(5000)
        emit(199)

        //flow can also collect other flows
//        firstFlow.collect {
//            emit(it)
//        }
        // more concise way to write above
        emitAll(firstFlow)
    }.collect {
        println(it)
    }

}

suspend fun main() {

    //flowBuilders() //uncomment to test flow builders

    //launchIn operator
    val flow = flow {
        delay(2000)
        emit(100)
        delay(200)
        emit(200)
    }

    val scope = CoroutineScope(EmptyCoroutineContext)
    flow.map { num ->  //map transforms data and returns another type of flow from one flow
        // and that is propagated downward for onEach.
        num * num
    }.onStart {
        println("onStart")
    }.onCompletion { cause ->
        //cause can have exception if didn't complete, else cause is null.
        println("onCompletion $cause")
    }.onEach {
        println("onEach $it")
    }.launchIn(scope)

    delay(5000) //Keeping thread alive until above flow completes.

//Below code behaves concurrent as in, other task is performed when there's delay in either of launch block
//    launch {
//
//        (1..5).forEach {
//            delay(200)
//            printWithTimePassed(it, currentTime)
//        }
//    }
//
//    launch {
//        listOf("1", "12", "12").forEach {
//            delay(200)
//            printWithTimePassed(it, currentTime)
//        }
//
//    }

}