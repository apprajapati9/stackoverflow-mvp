package com.apprajapati.mvp_stackoverflow.kotlin_playground.leetcode


fun isPalindrome(str: String): Boolean {
    //3 == 3/2 -= 1

    var counter = str.length / 2 //1, 0
    var left = 0
    var right = str.length-1 // 0, 1, 2

    while(counter>=1){
        val last = str[right] // 2[m]
        val first = str[left] // 0[m]
        if(first != last) return false
        left++
        right--
        counter--
    }
    return true
}

fun <T: Comparable<T>> isAnagrams(str1 : T, str2: T ) : Boolean {
    val one = str1.toString()
    val two = str2.toString()
    if(one.length != two.length){
        return false
    }
    val map1 = hashMapOf<Char, Int>()
    val map2 = hashMapOf<Char, Int>()
    for(i in one){
        if(map1.containsKey(i)){
            map1[i] = map1[i]!! + 1
        }else{
            map1[i] = 1
        }
    }
    for(i in two){
        if(map2.containsKey(i)){
            map2[i] = map2[i]!! + 1
        }else{
            map2[i] = 1
        }
    }

    return map1 == map2
}

fun main(){
    println("isPalindrome: -> ${isPalindrome("mom")}")
    println("isPalindrome: -> ${isPalindrome("3553")}")
    println("isPalindrome: -> ${isPalindrome("momoomom")}")

    val str1 = "heart"
    val str2 = "earth"

    val num1 = 123
    val num2 = 123

    println("isAnagrams - strings: -> ${isAnagrams(str1, str2)}")
    println("isAnagrams - numbers: -> ${isAnagrams(num1, num2)}")


}