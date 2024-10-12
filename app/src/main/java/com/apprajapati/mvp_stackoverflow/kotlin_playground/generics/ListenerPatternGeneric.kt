package com.apprajapati.mvp_stackoverflow.kotlin_playground.generics

interface Update {
    fun update(msg: String)
}

class Manager<T: Update> {
    val subscribers : ArrayList<T> = arrayListOf()

    fun register(subscriber: T) {
        subscribers.add(subscriber)
    }

    fun unregister(sub: T) {
        subscribers.remove(sub)
    }

    fun updateAll(msg: String){
        for(i in subscribers){
            i.update(msg)
        }
    }
}

class Dev(val name: String = "dev1") : Update{
    override fun update(msg: String) {
        println("$msg for $name ")
    }
}

class Dev2(val name: String = "dev2"): Update{
    override fun update(msg: String) {
        println("$msg for $name ")
    }
}

fun main(){
    val manager = Manager<Update>()
    manager.register(Dev2())
    manager.register(Dev())

    manager.updateAll("Sprint1 on work")

}