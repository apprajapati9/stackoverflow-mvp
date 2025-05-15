package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge.variance

interface Producer<out T> {
    fun getFullName(): T
}

interface C<in T> {
    fun consumeFullName(name: T)
}

class Ajay(val name: String? = "") : Producer<Any> {
    override fun getFullName(): Any {
        return name as Any
    }

}

class Ajay2(val name: String? = "") : C<Number> {
    override fun consumeFullName(name: Number) {
        println(name)
    }
}

//You can pass an object of consumer that is type of C<Number> because its supertype
fun processInt(consumer: C<Int>) {
    consumer.consumeFullName(122) //Its safe because its not returning `out` thus its safe to allow passing object of C of Number
}

fun main() {

    val ajay = Ajay("Ajay")
    println(ajay.getFullName())

    val ajay2 = Ajay2()
    ajay2.consumeFullName(12f)
    println(ajay2.name)

    processInt(ajay2)
}