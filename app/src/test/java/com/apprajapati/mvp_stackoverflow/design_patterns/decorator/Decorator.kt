package com.apprajapati.mvp_stackoverflow.design_patterns.decorator

import org.junit.jupiter.api.Test

/* Decorator pattern -
    It is used when you want to attach a new behavior to an object on top of what already exists
    or limitations of not being able to modify.
    You can also use this to override some behavior
 */

interface CoffeeMachine {
    fun makeSmallCoffee() : String
    fun makeLargeCoffee() : String
}

class NormalCoffeeMachine : CoffeeMachine{
    override fun makeSmallCoffee()  = "Small coffee"

    override fun makeLargeCoffee() = "Large coffee"
}

//Decorator which enhances or improves previous implementation
class EnhancedMachine(private val coffeeMachine: CoffeeMachine) : CoffeeMachine by coffeeMachine {
    override fun makeSmallCoffee(): String = "Enhanced small coffee with added sugar"

    fun makeMilkCoffee(){
        coffeeMachine.makeLargeCoffee()
        println("Added milk too.")
    }
}

class Decorator {
    @Test
    fun testDecorator(){
        val simpleMachine = NormalCoffeeMachine()
        val betterMachine = EnhancedMachine(simpleMachine)

        println(betterMachine.makeSmallCoffee()) // changing implementation
        println(betterMachine.makeLargeCoffee()) // keeping old functionality
        betterMachine.makeMilkCoffee() // adding a new behavior

    }
}