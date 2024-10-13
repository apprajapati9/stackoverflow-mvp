package com.apprajapati.mvp_stackoverflow.design_patterns.creational_patterns.lazy_init

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/* Lazy initialization pattern
 - Very useful technique for memory management because this allows you to have an object when needed
rather than storing it even when its not needed.
 - The main idea behind this pattern is that init a resource when it is needed, not when declared.

 Basic example :

 Eager init - instantiate when declared
    class Window {
        val box = AlertBox() //instantiated
        fun showMessage(message: String) {
            box.setMessage(message)
            box.build()
            box.show()
        }
    }

  Lazy init - instantiate when declared
    class Window {
        val box: AlertBox //declared only
        fun showMessage(message: String) {
            if(box == null)
                box = AlertBox()  // instantiated when needed to send message.
            box.setMessage(message)
            box.build()
            box.show()
        }
    }

Kotlin has built in lazy initialization. Important note: It can only be used with val, not var

    --> val box by lazy { AlertBox }

    if you want to use var then you hate to make sure to add lateinit to tell kotlin that
        it will be initialized later on which you must as a developer.

    --> lateinit var box : AlertBox   //lazy initialized using var.


 */

class AlertBox {
    var message : String ?= null

    fun show(){
        println("Alertbox $this: $message")
    }
}

//NOTE: this pattern should be used for bigger objects, for smaller objects, it is not worth to use this pattern.
class Window{
    val box by lazy { AlertBox() }

    fun showMessage(message: String){
        box.message = message
        box.show()
    }
}

class Window2{
    lateinit var box : AlertBox

    fun showMessage(message: String){
        box = AlertBox()
        box.message = message
        box.show()
    }

}

class LazyInitTest{

    @Test
    fun lazyInitTestForAlertBox(){
        val window = Window()
        Assertions.assertThat(window.box).isNotNull()
        window.showMessage("window")
        Assertions.assertThat(window.box).isNotNull

        val window2 = Window2()
        window2.showMessage("Window2")
        Assertions.assertThat(window2.box).isNotNull
    }
}