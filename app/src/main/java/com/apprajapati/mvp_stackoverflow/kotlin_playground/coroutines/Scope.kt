package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun <T> Iterable<T>.myFilter(predicate: (T) -> Boolean): Iterable<T> {
    val list = mutableListOf<T>()
    for (i in this) {
        if (predicate(i))
            list.add(i)
    }
    return list
}

suspend fun scopeTest() {

    coroutineScope {
        launch {
            delay(500L)
            println("A")
        }
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

fun main() = runBlocking {

    val numbers = listOf(1, 2, 3, 4, 5)
    val list = numbers.myFilter { num ->
        num % 2 == 0
    }
    print(list)
}