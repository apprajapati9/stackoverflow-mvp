package com.apprajapati.mvp_stackoverflow.design_patterns.proxy

import org.junit.jupiter.api.Test

/* Proxy design pattern - provide some functionality before and/or after calling an object
For example, 3 clients accessing a same file from disk class,
it could be inefficient so proxy design pattern can be like a middle man who caches the file
and provides a faster access of the file.

in a way it provides some extra functionality.
This pattern can be very similar to Facade pattern except the proxy has the same interface.
Similar to Decorator as well, but in proxy - it manages the lifecycle of its object
 */

interface Image{
    fun display()
}

class RealImage(private val fileName: String) : Image {

    override fun display() {
        println("RealImage: Displaying $fileName")
    }

    private fun loadFromDisk(fileName: String) {
        println("RealImage: Loading $fileName")
    }

    init {
        loadFromDisk(fileName)
    }
}

class ProxyImage(private val fileName: String): Image {

    private var realImage : RealImage ?= null

    override fun display() {
        println("ProxyImage: Displaying $fileName")
        if(realImage == null){
            realImage = RealImage(fileName)
        }
        realImage?.display()
    }

}

class ProxyTest {
    @Test
    fun testProxy(){
        val image = ProxyImage("ajay.jpg")

        //load image from disk.
        image.display()

        println("--------------")

        //load image from cache; This will not trigger loading, it will only display
        image.display()

    }
}