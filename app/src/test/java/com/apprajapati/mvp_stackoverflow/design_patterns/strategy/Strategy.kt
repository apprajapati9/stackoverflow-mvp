package com.apprajapati.mvp_stackoverflow.design_patterns.strategy

import com.apprajapati.mvp_stackoverflow.kotlin_playground.leetcode.toLowerCase
import org.junit.jupiter.api.Test

/* Strategy pattern :  A class behavior or algorithm that can be changed at runtime.

Useful when we want to add functionality without changing program structure or objects that we already have.
add and remove logic at runtime
 */

class Printer (private val formatter: (String) -> String){

    fun printMessage(message: String) {
        println(formatter(message))
    }
}

val lowercaseFormatter = { it: String ->
    it.toLowerCase()
}

fun capitalFirst(message: String) : String {
    val builder = StringBuilder()
    for(i in message.indices) //message.indices equates to 0..message.length
    {
        if(i == 0)
            builder.append(message[i].uppercase())
        else
            builder.append(message[i])
    }
    return builder.toString()
}

//same function as above but removed name to save it as variable

val lambdaFun = fun (message: String) : String {
    val builder = StringBuilder()
    for(i in message.indices) //message.indices equates to 0..message.length
    {
        if(i == 0)
            builder.append(message[i].uppercase())
        else
            builder.append(message[i])
    }
    return builder.toString()
}

val alternate = fun(message: String): String {
    val builder = StringBuilder()
    var alternate = true
    for(i in message.indices){
        if(alternate){
            builder.append(message[i].uppercase())
            alternate = false
        }else{
            builder.append(message[i].lowercase())
            alternate = true
        }
    }
    return builder.toString()
}

val firstNLast = {
    //inside brackets var: Type - will act as params of a lambda function.
    message : String ->
        val builder = StringBuilder()
        for(i in message.indices){
            if (i == 0 || i == message.length - 1){
                builder.append(message[i].uppercase())
            }else{
                builder.append(message[i].lowercase())

            }
        }
        builder.toString() //this act as return statement.
}

class Strategy {
    @Test
    fun strategyTest(){

        val string = "aJAY is a programmeR"

        println("----- Using function as lambda param for Printer ----")
        var formatter = Printer {
            capitalFirst(message = string) //passing a function as param - you can pass it like this as well.
        }
        formatter.printMessage(string)


        println("----- Using lambdaFun var to pass param to Printer ----")
        formatter = Printer(lambdaFun)
        formatter.printMessage(string)


        println("----- Using lowercaseFormatter var to pass param to Printer ----")
        formatter = Printer(lowercaseFormatter)//lambda var
        formatter.printMessage(string)

        println("----- Using ALTERNATE var to pass param to Printer ----")
        formatter = Printer(alternate)//lambda var
        formatter.printMessage(string)

        println("----- Using firstNLast var to pass param to Printer ----")
        formatter = Printer(firstNLast)//lambda var
        formatter.printMessage(string)
    }
}