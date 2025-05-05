package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

class Observer {

    private val observe = hashMapOf<String, () -> Unit>()

    fun addObserver(name: String, function: () -> Unit) {
        observe[name] = function
    }

    fun removeObs(name: String) {
        observe.remove(name)
    }

    fun trigger(name: String) {
        observe.forEach { (k, v) ->
            if (k == name) {
                v.invoke()
            }
        }
    }
}


fun main() {

    val obs = Observer()
    obs.addObserver("ajay1", {
        println("ajay1")
    })
    obs.addObserver("ajay2", {
        println("ajay2")
    })
    obs.addObserver("ajay3", {
        println("ajay3")
    })

    obs.trigger("ajay3")
}