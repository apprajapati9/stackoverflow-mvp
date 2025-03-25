package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge.variance

/*
https://kt.academy/article/ak-variance - Good read on Variance in Kotlin
 */
interface Animal {
    fun pet()
    fun name(): String
}

class Cat(val name: String) : Animal {
    override fun pet() {
        println("$name says meow")
    }

    override fun name(): String = name
}

//List<Cat> can be passed because List<out E> which means E can be of subtype of E and cat is subtype of Animal.
fun petAnimals(animals: List<Animal>) {
    animals.forEach {
        it.pet()
    }
}

//Error when you pass MutableList<Animal> with mutableList<Cat> because MutableList<E> expects invariant.
fun addAnimals(animals: MutableList<Animal>) {
    animals.add(Cat("Zozo"))
}


///////// in  - Cosumes

class Consumer<in T> {
    fun consume(value: T) {
        println("Consuming $value")
    }
}

/////

//
sealed class Response<out V>
class Success<V>(val value: V) : Response<V>()
class Failure(val error: Throwable) : Response<Nothing>()

fun handle(response: Response<Animal>) {
    val text = when (response) {
        is Success -> "Success with ${response.value.name()}"
        is Failure -> "Error"
        // else is not needed here
    }
    print(text)
}

//

fun main() {
    val cats: List<Cat> = listOf(Cat("Miko"), Cat("Twaila"))
    petAnimals(cats)


    val mutableCats: MutableList<Cat> = mutableListOf(Cat("Miko"), Cat("Twaila"))
    //addAnimals(mutableCats) //Error because fun accepts MutableList<Animal> and passing <Cat> and mutableList<E> is invariant


    //in

    val numberConsumer: Consumer<Number> = Consumer()
    numberConsumer.consume(2.71) // Consuming 2.71

    val intConsumer: Consumer<Int> = numberConsumer
    intConsumer.consume(42) // Consuming 42

    val floatConsumer: Consumer<Float> = numberConsumer
    floatConsumer.consume(3.14F) // Consuming 3.14


    val anyConsumer: Consumer<Any> = Consumer()
    anyConsumer.consume(123456789L) // Consuming 123456789

    val stringConsumer: Consumer<String> = anyConsumer
    stringConsumer.consume("ABC") // Consuming ABC

    val charConsumer: Consumer<Char> = anyConsumer
    charConsumer.consume('M') // Consuming M

    // val numbs = listOf(1,2,3,5)
    val response =
        Success(Cat("Meeko")) // practical example of out V, where Animal or any subtype is accepted thus passed Cat object.

    handle(response)
}