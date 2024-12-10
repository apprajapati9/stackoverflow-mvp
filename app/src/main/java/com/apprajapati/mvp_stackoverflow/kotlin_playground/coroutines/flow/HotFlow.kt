package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/*
Emission will happen when there's no collectors.
 */
fun main() {
    val sharedFlow = MutableSharedFlow<Int>()

    val scope = CoroutineScope(Dispatchers.Default)


    scope.launch {
        repeat(5) {
            println("emitted $it")
            sharedFlow.emit(it)
            delay(200)
        }
    }

    scope.launch {
        sharedFlow.collect {
            println("collected $it")
        }
    }

    scope.launch {
        sharedFlow.collect {
            println("collected $it")
        }
    }

    Thread.sleep(1500)
}
