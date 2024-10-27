package com.apprajapati.mvp_stackoverflow.design_patterns.callback_pattern

interface EventCallback {
    fun notify(message: String)
}

class Callee(private val callback : EventCallback) {

    fun doAsyncTask(){
        Thread.sleep(1000)
        callback.notify("from doAsyncTask")
    }
}

class EventCaller(private var callee: Callee? = null) : EventCallback {

    // private val callee : Callee = Callee(this) // <-- this seems more better way to init Callee

    fun doTask(){
        callee = Callee(this)
        callee?.doAsyncTask()

    }

    override fun notify(message: String) {
        println("Notified $message")
    }
}


fun main() {
    val caller = EventCaller()
    caller.doTask()
}