package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures


fun main() {

    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 15, 17, 20, 25, 50, 77, 80)
    val a2 = intArrayOf(1)

    val find = 55

    println(a.size)
    println(a2.size)

    search(a, 0, a.size - 1, find)
    println("---")
    search(a2, 0, a2.size - 1, 1)
}

fun search(a: IntArray, start: Int, end: Int, find: Int) {

    println("Iter")
    val mid = start + (end - start) / 2

    if (a[mid] == find) {
        println("Found at $mid with ${a[mid]}")
        return
    }

    if (start >= end) { //start != end doesn't work for same index or 1 element.
        println("Not found")
        return
    }

    if (a[mid] > find) {
        search(a, start, mid, find)
    } else {
        search(a, mid + 1, end, find)
    }
}
