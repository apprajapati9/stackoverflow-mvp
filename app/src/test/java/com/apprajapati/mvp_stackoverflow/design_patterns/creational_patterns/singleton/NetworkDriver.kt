package com.apprajapati.mvp_stackoverflow.design_patterns.creational_patterns.singleton

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/*
By using object, Network driver becomes a singleton
 */
object NetworkDriver {
    init {
        println("Init driver.. $this")
    }

    fun log(): NetworkDriver = apply {
        println("Network driver $this")
    }
}

/*
    This test will prove that only one instance will trigger the method log
    even with separate instantiation and variable.
 */

class SingletonTest {
    @Test
    fun testSingleton(){
        println("Start")
        //When using singleton, you don't need instantiation to use methods or members of Singleton class, so no need to have class().method
        val networkDriver = NetworkDriver.log()
        val networkDriver2 = NetworkDriver.log()

        //prints same object id for both $this in network driver.
        Assertions.assertThat(networkDriver).isEqualTo(networkDriver2)
    }
}