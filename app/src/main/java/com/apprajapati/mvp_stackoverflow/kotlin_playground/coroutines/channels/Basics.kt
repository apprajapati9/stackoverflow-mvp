package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines.channels

import kotlinx.coroutines.runBlocking


fun add(a: Int, b: Int, block: (Int) -> Unit) {
    block(a + b)
}

fun main(): Unit = runBlocking {

    add(12, 12) { ans ->
        println(ans)
    }
    println("Ajay")
}
