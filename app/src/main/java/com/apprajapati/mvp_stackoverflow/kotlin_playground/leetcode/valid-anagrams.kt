package com.apprajapati.mvp_stackoverflow.kotlin_playground.leetcode


//Constraint says string is lower case letters but you can convert to lowercase by using
//str.toLowerCase() extension function in this function.
fun main() {

    val string = "fe"
    val string2 = "ja"

    val string3 = "AjayY"

    println("With Hashmap -> ${isValidAnagramUsingMap(string, string2)}")

    println("WithoutMap -> ${withoutMap(string, string2)}")

    println("Uppercase $string3 -> ${string3.toLowerCase()}")
}

fun withoutMap(str1: String, str2: String): Boolean {

    if (str1.length != str2.length) {
        return false
    }

    var s2 = CharArray(str2.length)


    for (i in str2.indices) {
        s2[i] = str2[i]
    }

    for (i in str1.indices) {
        for (j in s2.indices) {
            if (str1[i] == s2[j]) {
                s2[j] = '0'
                break
            }
        }
    }

    for (i in s2) {
        println(i)
        if (i != '0') {
            return false
        }
    }

    return true
}

fun String.toLowerCase(): String {
    var mString = ""
    for (i in this) {
        if (i.code in 65..90) {
            val v = i.code + 32
            mString += v.toChar()
        } else
            mString += i
    }

    return mString
}


fun isValidAnagramUsingMap(string: String, string2: String): Boolean {


    val map1 = hashMapOf<Char, Int>()
    val map2 = hashMapOf<Char, Int>()

    if (string.length != string2.length) {
        return false
    }

    var size = string.length - 1
    println(size)
    while (size >= 0) {
        if (map1.contains(string[size])) {
            map1[string[size]] = map1.get(string[size])!!.plus(1)
        } else {
            map1[string[size]] = 1
        }

        if (map2.contains(string2[size])) {
            map2[string2[size]] = map2.get(string2[size])!!.plus(1)
        } else {
            map2[string2[size]] = 1
        }

        size -= 1
    }


    println("hashmp 1 and 2,  -> $map1 and $map2")

    if (map1.size != map2.size) {
        return false
    }

    for (i in map1) {
        if (!map2.contains(i.key) && map2.get(i.key) != i.value) {
            return false
        }
    }

    return true

}
/*
bool isAnagram(string s, string t) {
        if(s.size() != t.size()){
            return false;
        }

        char arr[t.size()];

        for(int i=0; i< t.size(); i++){
            arr[i] = t[i];
        }

        for(int i=0; i< s.size(); i++){
            for(int j=0; j< s.size(); j++){
                if(s[i] == arr[j]){
                    arr[j] = '0';
                    break;
                }
            }
        }

        for(auto i: arr){
            if(i!='0'){
                return false;
            }
        }

        return true;

    }
 */