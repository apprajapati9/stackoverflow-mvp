package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures

import kotlin.math.max

/*
    https://leetcode.com/problems/maximum-subarray/description/
 */


/*
Time Complexity : O(N2), where N is the number of elements in nums. There are N*(N+1)/2 total sub-arrays and trying out each one takes time of O(N2)
Space Complexity : O(1)
 */
fun solution1(numbers: IntArray): Int {  //ON2
    var ans = Int.MIN_VALUE

    numbers.forEachIndexed { index, num ->
        var currentSum = 0 // 5  -----
        for (i in index..numbers.size - 1) {
            currentSum += numbers[i] //5, 5+4 =9, 9+-10=-1,
            //println(currentSum)
            ans = max(ans, currentSum)
        }

    }
    return ans
}

fun solution2(numbers: IntArray): Int {
    //Kadane's Algo - On
    var currMax = 0
    var maxTillNow = Int.MIN_VALUE

    numbers.forEach {
        currMax = max(it, it + currMax)
        maxTillNow = max(maxTillNow, currMax)
    }
    return maxTillNow
}


fun main() {
    val arr = intArrayOf(5, 4, -10, 7, 8) //ans 15
    val arr2 = intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4) // ans 6
    val arr3 = intArrayOf(-1) // ans 1
    val arr4 = intArrayOf(5, 4, -1, 7, 8) // ans 23
    println(solution1(arr))
    println(solution1(arr2))
    println(solution1(arr3))
    println(solution1(arr4))

    println("====")
    println(solution2(arr))
    println(solution2(arr2))
    println(solution2(arr3))
    println(solution2(arr4))
}