package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED


fun fun3(con: () -> Unit) {
    print("Hello")
    con.invoke()
}

fun fun2(con: () -> Unit) {
    fun3 {
        print("world")
        con.invoke()
    }
}

fun fun1(con: () -> Unit) {
    fun2({
        print("!")
        con.invoke()
    })
}


fun main() {

//    fun1({
//        println("Ajay")
//    })

    // Compiler generates a new class that contains the suspendingLambda
    class Continuation1(val previousContinuation: Continuation<String>) : Continuation<Unit> {
        override val context = previousContinuation.context
        override fun resumeWith(result: Result<Unit>) {
            try {
                val returnedValue = suspendingLambda(previousContinuation as Continuation<Any?>)
                if (returnedValue != COROUTINE_SUSPENDED) {
                    previousContinuation.resumeWith(Result.success(returnedValue))
                }
            } catch (e: Exception) {
                previousContinuation.resumeWith(Result.failure(e))
            }
        }

        // Define a suspend lambda that returns "Hello World!"
        val suspendingLambda: (Continuation<Any?>) -> Any? = { continuation ->
            "Hello World!"
        }
    }

    // this gets converted into Continuation class with Any?
//    val suspendingLambda: suspend () -> String = suspend {
//        "Hello World!"
//    }


    //define a callback obj
    val callback = object : Continuation<String> {

        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            println(result.getOrNull())
        }

    }

    val continuation = Continuation1(callback)
    continuation.resumeWith(Result.success(Unit))


}