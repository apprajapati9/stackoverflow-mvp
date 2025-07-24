package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures

fun twoSum(numbers: IntArray, target: Int): IntArray { //O(n)
    val map = HashMap<Int, Int>()

    numbers.forEachIndexed { index, number ->
        //println("$number and $index")
        map[target - number]?.let {
            return intArrayOf(it, index)
        }
        map[number] = index
    }

    return intArrayOf()
}

fun main() {
    val vals = intArrayOf(2, 7, 11, 15)
    val target = 9

    val ans = twoSum(vals, target)
    println(ans.toList())
}