package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip


suspend fun main() {

    val flow1 = (1..10).asFlow().onEach { delay(1000) }
    val flow2 = (10..20).asFlow().onEach { delay(100) }

    //combine gets triggered when either of flow is triggered for both.
    // flow2 emits every 100ms
    // flow1 emits every 1 second
    // so total time it would take is 10 seconds..
    // flow 2 will be finished in 1 second only..
    // thus output will have 18,19,20 only..
    // and flow1 will trigger on every 1 second n print
    //So all in all, combine gets triggered when either of flow emits a value.
    flow1.combine(flow2) { //useful when you want to combine two states and combine them together to show on UI.
            num1, num2 ->
        listOf(num1, num2) // combined both.. as a result.
    }.collect { value ->
        println("combine- $value")
        //println("combine- $num2")
    }

    //exactly opposite is ZIP - when it will wait for both flow to emit and then collect will trigger
    //in this case it will print both values together in right order..
    flow1.zip(flow2) { num1, num2 ->
        listOf(num1, num2)
    }.collect {
        println("zip $it")
    }


    //either emissions, it will just collect and showcase. all emissions in one flow,
    // flow 2 emits first so it will show first.
    merge(flow1, flow2).onEach {
        listOf(it)
    }.collect {
        println("merge $it")
    }
}