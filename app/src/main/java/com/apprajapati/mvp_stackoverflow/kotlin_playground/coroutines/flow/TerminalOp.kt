package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking

fun main() {

    //collect
    val flow = flow {
        delay(100)
        emit(1)

        delay(100)
        emit(2)

        delay(100)
        emit(1)

        delay(100)
        emit(2)

        delay(100)
        emit(1)

        delay(100)
        emit(2)
    }

    runBlocking {
        flow.collect { //Collecting values.
            println(it)
        }

        val lst = flow.last() //gets last value in flow
        println(lst)

        //val singleItem = flow.single() // single operator gets single item, but must emit only 1 item from flow otherwise, it will throw illegalArgException

        val toSet = flow.toSet()
        println(toSet)

        val toList = flow.toList()
        println(toList)

    }

}