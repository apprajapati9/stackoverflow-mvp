package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge.delegation

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Delegate {
    operator fun getValue(ref: Any?, property: KProperty<*>): String {
        return "$ref, thanks for delegation ${property.name}"
    }

    //Ref - object upon which property is set
    //property - property name i.e var /val name.
    operator fun setValue(ref: Any?, property: KProperty<*>, value: String) {
        println("$value has been set to ${property.name} in this $ref")
    }
}

class Example {
    var p: String by Delegate()
}

class User {
    var name: String by Delegates.observable("<no name>") { prop, old, new ->
        println("$old -> $new")
    }
}

//Storing properties in a map.
class User2(attrs: Map<String, Any?>) {
    val name: String by attrs //Delegated name property to the 'attrs' using map
    val age: Int by attrs
}

class Resource {

    //Lazy is a delegation lambda function
    private val resource: String by lazy {
        println("Init resource")
        "init"
    }

    fun useResource() = "Using resource $resource"
}

fun main() {
    val e = Example()
    println(e.p)
    e.p = "ajay"
    
    //This is to demonstrate observable delegation
    val user = User()
    user.name = "first"
    user.name = "second"

    val user2 = User2(
        mapOf(
            "name" to "John Doe",
            "age" to 25
        )
    )

    println(user2.name) // Prints "John Doe"
    println(user2.age)  // Prints 25


    //To showcase lazy delegation
    val rUser = Resource()

    println(rUser.useResource())
    println(rUser.useResource())
}


/*
For more info read : https://kotlinlang.org/docs/delegated-properties.html#translation-rules-for-delegated-properties
 */