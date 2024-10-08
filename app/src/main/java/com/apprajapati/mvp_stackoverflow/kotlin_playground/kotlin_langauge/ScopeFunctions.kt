package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

import kotlin.random.Random

/*
    The kotlin standard library contains several functions whose sole purpose is to execute
    a block of code within the context of an object. When you call such a function on an object
    with a lambda expression provided, it forms a temporary scope, in this scope you can
    access the object without its name, and they are called scope functions.

    let : executing a lambda on non-nullable objects
          Introducing an expression as a variable in local scope
    apply : object configuration
    run : object configuration and computing the result
    also : additional effects
    with : grouping function calls on an object

 */

data class Person(val name: String, var age: Int, val from: String){
    fun incrementAge() = apply {
        age++
    }
}

fun getRandomValue(): Int {
    return Random.nextInt(1, 100)
}

fun getPersonName(p: Person) : String {
    println("Inside getPersonName - $p")
    return p.name
}

fun main(){

    //--USE OF let
    //let : executing a lambda on non-nullable objects
    //Introducing an expression as a variable in local scope
    val person = Person("Ajay", 30, "India").let {
        println(it.name) //accessing object within that object space
        it.incrementAge() //accessing objects methods in a local scope without its name.
        println(it)
        println(it.age)
    }

    // run : object configuration and computing the result
    person.run {
        println()
    }

    // you can change object's value using apply like this.
    val ajay = Person(age = 12, name = "Ajay", from = "India").apply {
        age = 15
    }
    println(ajay)

    //also reference the context object as a lambda argument.
    //  If the argument name is not specified, the object is accessed by the implicit
    //  default name it. it is shorter than this and expressions with it are usually easier
    //  to read.

    //also should have a returned value to capture and use
    getRandomValue().also {
        println("$it") // function returns an int number thus accessed using It
    }

    person.also {
        println(getRandomValue()) //allows you to execute a code statement along with the object
        println("object with it -> ${it.javaClass.name}") // person class has no returned value so default IT as UNIT.
    }

    //also - do something on returned value from an object.
    // thisObject.also {  // do something on returned value from thisObject }
    getPersonName(Person("ajay2", 12, "whatever")).also {
        println("returned $it")
        it.apply { //performed action on returned object it which is string.
            for(i in it){
                println(i)
            }
        }
    }

    //because incrementAge() has apply {} we can do the following
    val ajay3 = Person("ajay3", 1, "India").incrementAge()
        .incrementAge()
        .incrementAge()

        //also  - The context object is available as an argument (it).
        // - The return value is the object itself.
        .also {
            println(it.age) //4 age.
        }

    val numbers = (1..10).toMutableList()
    numbers.run {
        add(122)
        // run can have access to object using this
        for(i in this){
            println(i)
        }
    }

    //with some object perform operations on. example, operations on list of numbers
    with(numbers){
        val first = first()//this.get(0)
        val last = last() //this.get(this.size-1)
        println("first $first and last $last")
    }

    with(getRandomValue()){ //object is Int here returned value
        println("with $this")
    }
    with(ajay3){ //with works with some object..
        println("with ${this.age}")
    }
    with(person){ //DOESN'T work because of let {}, it doesn't just forms an object, it also adds let {} block that has Unit returned value thus returns Unit.
        println("with ${this}")
    }
}