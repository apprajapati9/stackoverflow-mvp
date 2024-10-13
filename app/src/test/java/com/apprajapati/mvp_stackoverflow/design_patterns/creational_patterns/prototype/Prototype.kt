package com.apprajapati.mvp_stackoverflow.design_patterns.creational_patterns.prototype

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/* Prototype Pattern

Allows you to copy existing objects in an efficient way without depending on their classes
It only relies on interface
The copied object must provide the copy functionality
 */


abstract class Shape : Cloneable {
    var id: String ?= null
    var type: String ?= null

    abstract fun draw()

    public override fun clone(): Any {
        var clone : Any? = null
        try {
            clone = super.clone()
        }catch (e: CloneNotSupportedException){
            e.printStackTrace()
        }
        return clone!!
    }
}

class Rectangle : Shape() {
    override fun draw() {
        println("Drawing Rectangle::draw()")
    }

    init {
        type = "Rectangle"
    }
}

class Square : Shape() {
    override fun draw() {
        println("Drawing Square::draw()")
    }

    init {
        type = "Square"
    }
}

class Circle : Shape() {
    override fun draw() {
        println("Drawing Circle::draw()")
    }

    init {
        type = "Circle"
    }
}

object ShapeCache {
    private val shapeMap = hashMapOf<String?, Shape>()

    fun loadCache(){
        val circle = Circle()
        val rect = Rectangle()
        val square = Square()

        shapeMap.put("1", circle)
        shapeMap.put("2", rect)
        shapeMap.put("3", square)

    }

    fun getShape(shapeId: String): Shape {
        val cachedShape = shapeMap.get(shapeId)
        return cachedShape?.clone() as Shape
    }
}

class PrototypeTest {
    @Test
    fun testPrototype(){
        ShapeCache.loadCache()

        val cloneShape1 = ShapeCache.getShape("1")
        val cloneShape2 = ShapeCache.getShape("2")
        val cloneShape3 = ShapeCache.getShape("3")

        cloneShape1.draw()
        cloneShape2.draw()
        cloneShape3.draw()

        Assertions.assertThat(cloneShape1.type).isEqualTo("Circle")
        Assertions.assertThat(cloneShape2.type).isEqualTo("Rectangle")
        Assertions.assertThat(cloneShape3.type).isEqualTo("Square")
    }
}