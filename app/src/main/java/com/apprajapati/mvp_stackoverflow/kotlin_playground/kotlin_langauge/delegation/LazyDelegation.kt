package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge.delegation

import kotlin.reflect.KProperty

/*
Lazily load large dataset.
Data loaded upon first access and then cached for the future access.
 */

class LazyLoadingDelegate<T>(private val loader: () -> T) {

    //backing filed to store the loaded data
    private var _value: T? = null

    //lock the object for thread safety
    private val lock = Any()


    /*
        Getter operator for the delegate

        ref - object containing the property
        property - property the metadata for the property
        return T - the loaded data.
     */

    operator fun getValue(ref: Any?, property: KProperty<*>): T {
        return _value ?: synchronized(lock) {
            _value ?: loader().also {
                _value = it
            }
        }
    }
}

class LargeDataSet {

    val data: List<String> by LazyLoadingDelegate {
        println("Loading large data set")
        List(10000) {
            "item $it"
        }
    }

    fun processData() {
        println("data size is ${data.size}")
    }
}

fun main() {
    val largeDataset = LargeDataSet()
    //data is not created yet.

    largeDataset.processData() //Upon first access, data will be created.

    println("Accessing data again") // It will not trigger loading again. Cached obj will be returned.

    largeDataset.processData()
}