package com.apprajapati.mvp_stackoverflow.design_patterns.builder

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/*
    This pattern is useful when you have lots of parameters and it is impractical to add them
    all in constructor or you want to control/add whatever needed params only, optional ones can be omitted.

    Pseudocode for Builder pattern
    class Component private constructor(builder: Builder){
        var param1
        var param2
        var param3

        class Builder {
            var param1
            var param2
            var param3

            fun setParam1
            fun setParam2
            fun setParam3

            fun build() = Component(this)
        }

        init {
            param1 = builder.param1
                    ...
            paramN
        }
    }

 */

//Outer class gets instantiated first and then inner class because inner class holds reference to its outer class
class Shape private constructor(builder: BuildShape) {

    var shapeName : String ? = null
    var shapeColor : Int ?= 0
    var shapeEdges : Int ?= 0

    class BuildShape {
        //params with default values
        private var shapeName: String ?= null
        private var shapeColor: Int ?= 0
        private var edges : Int ?= 0  //circle

        //apply is what allows you to chain those functions like this .setname().setcolor().set().build()
        //alternatively this can be done while instantiating too when building.
        fun setShapeName(name: String) = apply {  this.shapeName = name }
        fun setShapeColor(color: Int) = apply {  this.shapeColor = color }
        fun setShapeEdges(edges: Int) = apply {  this.edges = edges }
        ///TODO: Look more into apply

        fun getShapeName() = shapeName
        fun getShapeColor() = shapeColor
        fun getEdges() = edges

        //This returns an instance of this class which Shape needs.
        fun build(): Shape = Shape(this)
    }

    init {
        shapeName = builder.getShapeName()  //kotlin already provides get() and set() method when defined class
        shapeColor = builder.getShapeColor()
        shapeEdges = builder.getEdges()
    }

}

class Draw{
    @Test
    fun testShape(){
        //since constructor is private, we are forced to use BuildShape().
        //can also check Paint class of android because Paint uses builder pattern.
        val star = Shape.BuildShape()
            .setShapeName("Star") // this in a way using apply {} block that' s why we can chain this function calls
            .setShapeColor(122)
            .setShapeEdges(5).build()

        Assertions.assertThat(star.shapeName).isEqualTo("Star")
        Assertions.assertThat(star.shapeColor).isEqualTo(122)
    }
}
