package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlin.coroutines.suspendCoroutine


suspend fun main() {

    println("First")
    suspendCoroutine<Unit> {
        print("Ajay")
    }
    print("Last")

}

fun printSomething() {
    println("Gone to printSomething")
}