package com.apprajapati.mvp_stackoverflow.kotlin_playground.leetcode


fun containsDuplicate(array: Array<Int>): Boolean {

    val mMap = HashMap<Int, Int>()
    var isIt = false;

    array.forEach {

        if (mMap.contains(it)) {
            isIt = true
        } else {
            mMap[it] = 1
        }
    }
    println(mMap)

    return isIt
}

fun containsUsingArray(nums: Array<Int>): Boolean {
    for (i in nums.indices) {
        nums.forEachIndexed { index, num ->
            if (index != i && nums[i] == num) {
                println(" ${nums[i]}")
                return true
            }
        }
    }
    return false
}


fun main() {

    val nums = arrayOf(10, 20, 30, 10)
    println("Using hashmap-> ${containsDuplicate(nums)}")
    println("Using array-> ${containsUsingArray(nums)}")
}