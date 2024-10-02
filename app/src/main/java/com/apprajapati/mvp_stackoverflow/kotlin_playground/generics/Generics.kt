package com.apprajapati.mvp_stackoverflow.kotlin_playground.generics

class Generics {
    // <T> mentions that this is going to be a generic type
    fun <T> showValue(value : T) {
        println("this is the $value")
    }

    //bounded type params - restricts passing values other than ones that extends Number
    fun <T: Number> numberSquare(num: T) {
        val square = num.toDouble() * num.toDouble()
        println("num + num is ${square}")
    }

    fun <T: Number> square(num: T) : Double {
        return num.toDouble() * num.toDouble()
    }
    
    fun <T: Comparable<T>> countGreater(items: Array<T>, item: T) : Int {
        var counter = 0

        for(t : T in items){
            if(t > item) {
                counter++
            }
        }
        return counter
    }

}

//class MathOperations<T> {
//
//    fun add(num1 : T, num2 : T) : Int {
//        return num1 + num2
//    }
//}

//Example for double generics values
class Table<K,V>() {

    var key: K ?= null
    var value: V ?= null

    constructor(key: K, value: V) : this() {
        this.key = key
        this.value = value
    }

    override fun toString(): String = "Table Key, Value pair -> [$key,$value]"
}

/*
    When you specify generic, < > or + - * operations don't work because value or type isn't
    known. That's why it is important to define a bound type so we can perform such operations

    you can define bounds by  <T : Comparable<T>>  - here's comparable<T> is bound type
    saying that given type extends Comparable<T> which allows us to perform comparison operations
 */
fun <T : Comparable<T>> calculateMin(num1: T, num2: T) : T {
    // 0 means first one is smaller, check compareTo method for flags, compareTo method is replaced with < >
    if(num1 < num2){  //num1.compareTo(num2) < 0 would work the same.
        return num1
    }
    return num2
}




fun main(){
    val generics = Generics()

    generics.showValue("ajay")
    generics.numberSquare(12.1)
    println("square with return double -> ${generics.square(12)}")

    //--------- Class Table<K,V>
    val table = Table("Ajay",9)
    val table2 = Table(12, 12f)
    val table3 : Table<String, Int> = Table()
    table3.key= "ajay3"
    table3.value = 9

    println(table3)
    println(table.toString())
    println(table2.toString())
    // --------- End..

    //-----Bounded type param in generics
    val num1 = 12.1
    val num2 = 11.1
    println(calculateMin(num1, num2)) // when bounded type, it says T so all values have to be of type 1 i.e if Int then second number must be Int as well.
    // ----- End..

    //coding CountGreaterItems method in generics - return number of items that are greater than given number.
    val array = arrayOf(1,2,3,4,9,10)
    println(generics.countGreater(array, 10))
    // ---- End..


}