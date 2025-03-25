package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge.variance

open class Dog {
    val name = "Dog"
}

class Hound : Dog()

fun takeDog(dog: Dog) {}

class Puppy : Dog() { //Sub type

}

//Invariant - expects exact type
class Box<T : Dog> { //out = subtype, in would reverse

}

//Covariance - expects any type that is of subtype of T
class Box2<out T> {
    private var value: T? = null

    /*

    fun put(value: T) { //Compilation error
        this.value = value
    }

     */

    fun get(): T = value ?: error("Value not set")
}

class Box3<in T> { // will prevent you from creating object with subtype. i.e reverse the relationship

    private var value: T? = null

    fun put(value: T) {
        this.value = value
    }
}


fun main() {

    val d = Dog()
    val p = Puppy()

    val b = Box<Dog>()
    //val b2: Box<Dog> = Box<Puppy>()  //ERROR

    val b3: Box2<Dog> = Box2<Puppy>() //No error because "out" covariance
    // val b6: Box2<Puppy> = Box2<Dog>() //error because "out" covariance, Dog is supertype.

    // val b4: Box3<Dog> = Box3<Puppy>() //Error, not super type. in would reverse the relationship
    val b5: Box3<Puppy> = Box3<Dog>() //No Error, because Dog is the supertype of Puppy

    val b7: Box2<Number> = Box2<Int>() //Fine because Int is subtype.
    // val b8: Box2<Int> = Box2<Number>() //Error because its not subtype, its supertype

    val b9: Box3<Int> = Box3<Number>() // No error because Number is actually supertype.

    takeDog(Dog())
    takeDog(Puppy())
    takeDog(Hound())

    val dogBox = Box3<Dog>()
    dogBox.put(Dog())
    dogBox.put(Puppy())
    dogBox.put(Hound())

    val puppyBox: Box3<Puppy> = dogBox
    puppyBox.put(Puppy())

    val houndBox: Box3<Hound> = dogBox
    houndBox.put(Hound())
    /*
    However, public in-positions cannot be used with covariance, including the out modifier. Just think what would happen if you could upcast Box<Dog> to Box<Any?>. If this were possible, you could literally pass any object to the put method. Can you see the implications of this? That is why it is prohibited in Kotlin to use a covariant type (out modifier) in public in-positions.
     */
}

/*
// Java
Integer[] numbers= {1, 4, 2, 3};
Object[] objects = numbers;
objects[2] = "B"; // Runtime error: ArrayStoreException

As you can see, casting numbers to Object[] didn't change the actual type used inside the structure (it is still Integer); so, when we try to assign a value of type String to this array, an error occurs. This is clearly a Java flaw, but Kotlin protects us from it by making Array (as well as IntArray, CharArray, etc.) invariant (so upcasting from Array<Int> to Array<Any> is not possible).
 */