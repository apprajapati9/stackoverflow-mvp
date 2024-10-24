package com.apprajapati.mvp_stackoverflow.design_patterns.callback_pattern

import kotlin.random.Random

interface Callback{
    fun success(msg: String)
    fun error(msg: String)
}

class Download(private val callback: List<Callback>) {

    fun getFile(){
        val delay = Random.nextLong(1,1500)
        println("task time -> $delay")
        Thread.sleep(delay)
        if(delay < 1000){
            for(i in callback){
                i.error("Callback failed")
            }
        }else{
            for(i in callback){
                Thread.sleep(delay + Random.nextLong(1,600))
                i.success("Got file")
            }
        }

    }
}

class Caller(val name : String) : Callback {
    override fun success(msg: String) {
        println("$name: download completed -> $msg")

    }

    override fun error(msg: String) {
        println("$name: download failed -> $msg")
    }

}

fun main(){

//    val downloadFile = Download(object: Callback {
//        override fun success(msg: String) {
//            println("download completed -> $msg")
//        }
//
//        override fun error(msg: String) {
//            println("download failed -> $msg")
//        }
//    })

    val caller1 = Caller("C1")
    val caller2 = Caller("C2")
    val caller3 = Caller("C3")
    val downloadFile = Download(listOf(caller1, caller2, caller3))
    //val caller = Caller()

    downloadFile.getFile()
}
