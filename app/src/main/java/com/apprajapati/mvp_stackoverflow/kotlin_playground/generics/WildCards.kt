package com.apprajapati.mvp_stackoverflow.kotlin_playground.generics

/* Subtyping : Subtyping is a fundamental principle in OOP
- one type is a subtype of another if they are related by an extends(: in Kotlin) or
implements clause (: in kotlin)
 - Int is a subtype of Number
 - ArrayList<E> is a subtype of List<E>
 - List<E> is a subtype of Collection<E>
 because they either extend other class or implement interfaces.
 Transitive property exists which means ArrayList is a subtype of Collection as well

 */

fun <T> print(items: ArrayList<T>){
    for(i in items){
        println(i)
    }
}

interface Shape{
    fun draw()
}

class Circle : Shape{
    override fun draw() = println("Drawing Circle")
}

class Rectangle : Shape{
    override fun draw() = println("Drawing Rectangle")
}

class Square : Shape{
    override fun draw() = println("Drawing Square")
}

class Triangle : Shape{
    override fun draw() = println("Drawing Triangle")
}

fun drawAll(shapes: ArrayList<Shape>){
    for(shape in shapes){
        shape.draw()
    }
}

fun <T : Shape> drawTAll(shapes: ArrayList<T>) {
    for(shape in shapes){
        shape.draw()
    }
}
/*
    declaration site variance - Out keyword.
    out Shape meaning that ArrayList can have type that returns Shape
    so we are enabling it to have a subtype of shape as list as well, not just Shape.

    out modifier is called a variance annotation and since it is provided at the type
    parameter declaration site, it provides declaration-site variance.
 */
fun drawAllInferType(shapes: ArrayList<out Shape>){
    for(shape in shapes){
        shape.draw()
    }
}

interface Source<out T> {
    fun nextT(): T
}

fun demo(items: Source<String>) {
    val objects : Source<Any> = items // instead of Object, kotlin has Any.
}

fun main(){
    val list = arrayListOf(1,2,3,4)
    print(list)

    val shapes = arrayListOf(Square(), Circle(), Rectangle(), Circle())
    drawAll(shapes) //in this case we cannot pass list of type Circle or any concrete class

    val triangle = arrayListOf(Triangle())

    //drawAll(triangle) //Error :inferred type is ArrayList<Triangle> but expected ArrayList<Shape>, because its of type circle, not shape.

    //solutions for above error...
    drawTAll(triangle) // by providing <T:Shape> as generic, that problem can be solved as well.
    drawAllInferType(triangle) //solved using Out keyword --> No error because of <out Shape> in the method


}