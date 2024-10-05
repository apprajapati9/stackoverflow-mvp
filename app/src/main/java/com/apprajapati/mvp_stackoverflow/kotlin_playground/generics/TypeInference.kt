package com.apprajapati.mvp_stackoverflow.kotlin_playground.generics

class Bucket<T> {
    var item : T ?= null
}

fun <T> addStore(item: T, list: ArrayList<Bucket<T>>) {
    val bucket = Bucket<T>()
    bucket.item = item
    list.add(bucket)
    println(" ${item.toString()} has been added to list..")
}
/* Under the hood with type inference when we specify string, it is going to replace type T with String/Int or any specified
fun <String> addStore(item: String, list: ArrayList<Bucket<String>>) {
    val bucket = Bucket<String>()
    bucket.item = item
    list.add(bucket)
    println(" ${item.toString()} has been added to list..")
}
 */
// you can specify - <T : Comparable<T> but <T> add is fine as well
fun <T> add(list: ArrayList<T>, item1: T, item2: T): List<T> {
    list.add(item1)
    list.add(item2)
    return list
}

fun main(){

    val list : ArrayList<Bucket<String>> = arrayListOf()
    //because of type inference, no need to specify arrayListOf<Bucket<String>>
    //if you remove -- : ArrayList<Bucket<String>> then you have to specify because it cannot infer type

    addStore("Ajay", list)
    addStore("Elisha", list)
    addStore("ajay", list)

    for(i in list){
        println(" ${i.item}")
    }

    //Type infer list
    val intList = add(arrayListOf(), 12, 3) //inferring type as Int based on given params
    println(intList)
}
