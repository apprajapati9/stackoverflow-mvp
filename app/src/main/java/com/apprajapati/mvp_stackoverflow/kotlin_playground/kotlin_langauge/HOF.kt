package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge


//get first of the condition that met within lambda predicate(T)
// here predicate (T) if for example Int then expects an expression that produces value of type T (Int) in this case.
inline fun <T> Iterable<T>.getFirstIf(predicate: (T) -> Boolean): T {
    for (element in this) if (predicate(element)) return element
    throw NoSuchElementException("Collection contains no element matching the predicate.")
}

fun String.firstVowel(): Char {
    for(i in this){
        if(i == 'a' || i == 'e' || i == 'o' || i == 'i' ||i == 'u' ){
            return i
        }
    }
    throw NoSuchElementException("No Vowel exists in this string!")
}

/*
    Expression : something that produces value
        i.e  number % 2 == 0 --> value that is even
             number > 4

    Statement : that doesn't produce any value
            println("whatever")
 */

//Check Strategy.kt file for some kotlin style Generic function implementation.
fun main(){
    val list  = mutableListOf("ajay1","ajay3")
    val str = list.getFirstIf {
        it == "ajay1" //T of type string so such expression
    }
    println(str)

    val list2  = mutableListOf(1,2,3)
    val str2 = list2.getFirstIf {
        it%2 == 0 //based on type T of mutable list, condition is diff in predicate(T)
    }
    println(str2)

    val name = "rwxxax"
    println(name.firstVowel())


}