package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

fun main() {
    var counter = 1

    val time = System.currentTimeMillis()

    while(counter < 1000){
        Thread.sleep(100) // 100000/ 1000 -> 100 seconds
        counter++ // 10 - 1 .. 1000 ?  100
        println("ajay -> $counter")
    }
    val finishTime = System.currentTimeMillis()

    val totalTime = (finishTime - time) / 1000
    println(totalTime)
}