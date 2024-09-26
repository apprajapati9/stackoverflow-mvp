package com.apprajapati.mvp_stackoverflow.design_patterns.bridge

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/* Bridge design pattern
 */

interface Device{
    var volume : Int
    fun getName() : String
}

class Radio : Device {

    override var volume: Int = 0

    override fun getName(): String =  "Radio $this"
}

class TV : Device {
    override var volume: Int = 0

    override fun getName(): String = "TV $this"
}

interface Remote {
    fun volumeUp()
    fun volumeDown()
}

class BasicRemote(val device: Device): Remote {
    override fun volumeUp() {
        if(device.volume <= 100){
            device.volume++
        }
        println("${device.getName()} volume Up-> ${device.volume}")
    }

    override fun volumeDown() {
        if(device.volume > 0 ) {
            device.volume--
        }
        println("${device.getName()} volume Down-> ${device.volume}")

    }

}

class BridgeTest {

    @Test
    fun testBridge(){
        val tv = TV()
        val radio = Radio()

        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)

        tvRemote.volumeUp()
        tvRemote.volumeDown()
        tvRemote.volumeDown()

//        for(i in 0..101)
//            radioRemote.volumeUp()
        radioRemote.volumeUp()

        radioRemote.volumeDown()

        Assertions.assertThat(radio.volume).isEqualTo(0)
    }
}