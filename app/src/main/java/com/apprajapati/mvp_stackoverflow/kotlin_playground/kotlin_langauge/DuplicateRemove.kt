package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

fun isEven(value: Int, predicate: (Int) -> Boolean): Boolean {
    return predicate(value)
}

fun main() {

    val list = listOf(1, 1, 3, 2, 3, 1, 1, 2, 3).toSet()
    val list2 = listOf(1, 1, 3, 2, 3, 1, 1, 2, 3).distinct()
    val list3 = listOf(1, 1, 3, 2, 3, 1, 1, 2, 3).toHashSet()

    println(list)
    println(list2)
    println(list3)

    val number = 13
    val answer = isEven(number) {
        it % 2 == 0
    }
    println("is $number even? -> $answer")

}