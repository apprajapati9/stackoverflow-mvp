package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

fun guide() {
    println("Guide start")

    teachNotInline {
        println("teach")
    }

    teachInline {
        println("Inline teach")
    }
    println("Guide ends")
}

inline fun teachInline(value: () -> Unit) {
    value.invoke()
}

fun teachNotInline(function: () -> Unit) {
    function.invoke()
}

data class Colors(val color: String)

fun main() {
    //Decompile to check and compare java code for inline and not inline functions.
    guide()

    val c1 = Colors("Red")
    val c2 = Colors("Red")

    println(c1 == c2) // they are equal because data are same.
    println(c2 === c1) // false because ref are diff.

}