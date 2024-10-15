package com.apprajapati.mvp_stackoverflow.design_pattern_exercises

import java.math.BigDecimal

/*
    Design a class Order.kt to place an order with buy and sell prices. Prices can be in Bigdecimal
    restrict creation of constructor
    print the order placed
 */

class Order private constructor(val buy: BigDecimal, val sell : BigDecimal) {

    init {
        println("order is ${buy}, ${sell}")
    }


    companion object {
        fun createOrder(buy: BigDecimal, sell: BigDecimal) = Order(buy, sell)
    }
}

fun main(){
    val order = Order.createOrder(BigDecimal("1234"), BigDecimal("1234"))
}