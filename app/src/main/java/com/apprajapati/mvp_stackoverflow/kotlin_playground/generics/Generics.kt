package com.apprajapati.mvp_stackoverflow.kotlin_playground.generics

class Generics {
    // <T> mentions that this is going to be a generic type
    fun <T> showValue(value : T) {
        println("this is the $value")
    }

    //bounded type params - restricts passing values other than ones that extends Number
    fun <T: Number> numberSquare(num: T) {
        val square = num.toDouble() * num.toDouble()
        println("num + num is ${square}")
    }

    fun <T: Number> square(num: T) : Double {
        return num.toDouble() * num.toDouble()
    }

}

fun main(){
    val generics = Generics()

    generics.showValue("ajay")
    generics.numberSquare(12.1)
    println("square with return double -> ${generics.square(12)}")
}