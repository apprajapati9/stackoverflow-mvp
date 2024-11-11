package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

/*
    Invoke keyword allows us to treat function as objects and manipulate them in variety of ways.
    Useful when:
        1. Passing function as arguments to other functions.
        2. Storing functions in data structures : practical example being Livedata where livedata
            stores the function pointer/reference as observers to trigger update events on objects.
            //OwnLiveData repo on Github.
        3. creating DSLs (Domain specific language) creating specialized languages that can be used to solve
            specific problems.
 */

val sumInt: (Int, Int) -> Int = { num1, num2 ->
    num1+num2
}

val sumFloat  = { num1: Float, num2: Float ->
    num1+num2
}

fun <T> sum(num1: T, num2: T, sumFunction: (T, T) -> T): T {
    return sumFunction.invoke(num1, num2) //return sumFunction(num1,num2) will work as well.
}

// explicitly telling param and return type.
val greetings : (String) -> String = {
    name ->  "Hello $name"
}

// without specifying type and return.
val greeting = {
        name: String -> "Hello $name"
}

fun add(n1: Int, n2: Int) = n1+n2

fun main(){

    println(sum(12,12, sumInt))
    println(sum(12.3f, 12.3f, sumFloat))

    println(sum(11,11, ::add)) // useful point number 1: passing function as param

    println(greetings("Kotlin"))
    println(greetings.invoke("Ajay"))

    val sum = ::add.invoke(12,12) // :: gives reference of the function add
    println(sum)
}