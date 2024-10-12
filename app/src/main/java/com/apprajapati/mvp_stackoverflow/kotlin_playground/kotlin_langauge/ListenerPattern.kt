package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
    Just attempt to learn and implement Listener pattern myself.
 */

interface Update{
    fun update(msg: String)
//    suspend fun isWorkDone(): Boolean
}

class Manager {
   // val list = arrayListOf<Update>()

    val list = hashMapOf<String, Update>()

    fun register(name: String, listener: Update){
        //list.add(listener)
        list[name] = listener
    }

    fun unregister(name: String){
        //list.remove(listener)
        list.remove(name)
    }

    fun updateAll(msg: String) {
        for(i in list){
            i.value.update(msg)
        }
    }

    //TODO: could send an update to specific dev
    fun updateSpecific(name: String, msg: String){
        for(i in list){
            if(i.key == name){
                i.value.update(msg)
            }
        }
    }

    fun inquiry(){
        for(i in list) {

        }
    }
}

class Developer(val name :String = "Developer") : Update {

    private val _isSprintDone = MutableStateFlow(false)
    var isSprintDone = _isSprintDone.asStateFlow()

    override fun update(msg: String) {
        println("$name is updated with message $msg")
    }

    suspend fun workUpdate(){
        CoroutineScope(Dispatchers.IO).launch {
            _isSprintDone.emit(false)
            delay(1000)
            _isSprintDone.emit(true)
            delay(2000)
            _isSprintDone.emit(false)
            delay(1000)
            _isSprintDone.emit(true)
            println("Done emitting..")
        } //.join() // this will ensure that main thread waits until this thread finishes - just turn main into suspend function.
    }

}

//runBlocking {} will ensure that all coroutines finish and make main thread wait until that.
suspend fun main() {
    val manager = Manager()

    val dev1 = Developer("ajay1")
    val dev2 = Developer("ajay2")

    manager.register(dev1.name, dev1)
    manager.register(dev2.name, dev2)

    manager.updateAll("Start sprint 1")

    dev1.workUpdate()
    dev2.workUpdate()


//    //delay(3000)
   dev1.isSprintDone.collect {
        isDone ->
            manager.updateSpecific(dev1.name, "is it done? $isDone")
    }
//
//    dev2.isSprintDone.collect {
//            isDone ->
//        manager.updateSpecific(dev1.name, "is it done? $isDone")
//    }
//
//    delay(10000)
}
