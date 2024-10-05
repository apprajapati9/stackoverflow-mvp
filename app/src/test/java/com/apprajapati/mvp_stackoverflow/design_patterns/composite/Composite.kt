package com.apprajapati.mvp_stackoverflow.design_patterns.composite

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/* Composite design pattern
   Compose objects into tree structure to achieve some functionality
   It is similar to thinking like when we have a different parts and if we put together,
   there could be some data that we can use which is composite pattern.

   it allows yus to manipulate many objects as a single one.
 */

open class Equipment (
    open val price : Int,
    val name: String
)

open class Composite(name: String) : Equipment(0, name) {
    private val equipments = arrayListOf<Equipment>()

    override val price: Int
        get() = equipments.map { it.price }.sum()

    fun add(equipment: Equipment) = apply {
        equipments.add(equipment)
    }
}

class Computer: Composite("PC")
class Processor : Equipment(1000, "Intel")
class Hardware : Equipment(100, "Hard drive")

class Memory : Composite("Memory")
class SSD : Equipment(200, "Microsoft")
class RAM : Equipment(200, "Random Access Memory")



//Composite pattern - where we used price of all to determine computer price.
class CompositeTest {

    @Test
    fun testComposite() {

        val memory = Memory()
        memory.add(SSD())
            .add(RAM())

        val computer = Computer()
        computer.add(Processor())
            .add(Hardware())
            .add(memory)


        println(computer.price)

        Assertions.assertThat(computer.price).isEqualTo(1500)
    }
}