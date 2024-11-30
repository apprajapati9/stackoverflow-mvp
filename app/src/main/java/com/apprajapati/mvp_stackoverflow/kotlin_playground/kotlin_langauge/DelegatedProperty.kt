package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

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
class User2(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

fun main() {
    val e = Example()
    println(e.p)
    e.p = "ajay"

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
}


/*
For more info read : https://kotlinlang.org/docs/delegated-properties.html#translation-rules-for-delegated-properties
 */