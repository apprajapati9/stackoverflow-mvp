package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures

data class Node<T : Any>(
    var value: T,
    var next: Node<T>? = null
) {
    override fun toString(): String {
        return if (next != null) {
            "$value -> ${next.toString()}"
        } else {
            "$value"
        }
    }

    fun printInReverse() {
        next?.printInReverse()

        if (next != null) {
            print(" -> ")
        }
        print(value.toString())
    }
}

fun main() {

    val head = Node(1, null)
    val two = Node(2, null)
    val three = Node(3, null)
    val four = Node(4, null)

    head.next = two
    two.next = three
    three.next = four

    head.printInReverse()
    println()
    println(head)
}