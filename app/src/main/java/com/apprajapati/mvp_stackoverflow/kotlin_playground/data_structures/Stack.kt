package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures

interface Stack<T : Any> {
    fun pop(): T?
    fun push(element: T)
    fun peek(): T?
    val count: Int
    val isEmpty: Boolean get() = count == 0
}

//LIFO - Last in, first out
class StackImpl<T : Any> : Stack<T> {

    val stack = arrayListOf<T>()


    override fun pop(): T? {
        return stack.removeLastOrNull()
    }

    override fun push(element: T) {
        //println("Element -> $element")
        stack.add(element)
    }

    override fun peek(): T? {
        return stack.lastOrNull()
    }

    override val count get() = stack.size
}

fun printStack(stackList: ArrayList<Char>) {
    println("Printing..")
    for (i in stackList) {
        println(i)
    }
    println("Printing..ends.")
}


fun String.validParenMore(): Boolean {

    val parentsPair: Map<Char, Char> = mapOf(
        ')' to '(',
        '}' to '{',
        ']' to '[',
    )

    val stack = StackImpl<Char>()

    for (char in this) {

        if (parentsPair.contains(char)) {
            if (stack.isEmpty.not() && stack.peek() == parentsPair[char]) {
                stack.pop()
            } else
                return false
        } else
            stack.push(char)
    }

    return stack.isEmpty
}


fun String.validParen(): Boolean {
    val stack = StackImpl<Char>()

    for (char in this) {
        //  println("$char")
        when (char) {
            '(' -> {
                stack.push(char)
                //printStack(stack.stack)
            }

            ')' -> {
                if (stack.isEmpty) {
                    println("empty at ) true ${stack.count}")
                    return false
                } else
                    stack.pop()

                //printStack(stack.stack)
            }

            else -> {
                // println("Else")
            }
        }
    }
    // println("Size of stack -> ${stack.count}")
    return stack.isEmpty
}

fun main() {

    val name = "((aj)a(y)"

    val name2 = "[(])"
    val name3 = "[()]"

    println(name.validParen())
    println(name2.validParenMore())
    println(name3.validParenMore())
}