package com.apprajapati.mvp_stackoverflow.kotlin_playground

data class V(var x: Float, var y: Float) {
    fun scale(scale: Float) {
        this.x *= scale
        this.y *= scale
    }
}

fun <T> ArrayList<T>.myEach(function: (T) -> Unit) {
    for (i in this) {
        function(i)
    }
}

fun V.toCenter() {
    this.x /= 2
    this.y /= 2
}

fun V.getXYLambda(function: (Float, Float) -> Unit) {
    function(this.x, this.y)
}

fun main() {
    val list = arrayListOf<Int>()

    for (i in 1..4) {
        list.add(i)
    }

    list.myEach { num ->
        println("this is $num from lambda function")
    }

    val vector = V(100f, 100f)
    println("${vector.x}, ${vector.y}")
    vector.toCenter()
    println("${vector.x}, ${vector.y}")

    vector.getXYLambda { x, y ->
        println("this is $x and this is $y")
    }

    vector.scale(4f)
    println("${vector.x}, ${vector.y}")
}