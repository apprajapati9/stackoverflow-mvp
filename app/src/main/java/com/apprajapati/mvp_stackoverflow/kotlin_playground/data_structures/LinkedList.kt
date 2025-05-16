package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures

data class Node<T : Any>(
    var value: T, var next: Node<T>? = null
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

class LinkedList<T : Any>(
    var head: Node<T>? = null, var tail: Node<T>? = null, var length: Int = 0
) {

    override fun toString(): String {
        return "Head -> ${head?.value}, tail -> ${tail?.value}, len = $length"
    }

    fun add(node: Node<T>?) = apply {
        var temp = head

        if (node != null) {

            if (temp == null) {
                head = node
            }

            while (temp?.next != null) {
                temp = temp.next
            }


            temp?.next = node
            tail = temp?.next
            length += 1

        }
    }

    fun insert(index: Int, node: Node<T>?) {
        var temp = head

        var counter = 0
        while (temp?.next != null && counter < index - 1) {
            temp = temp.next
            counter++
        }

        print("Inserting at -> ${temp?.value}")
        node?.next = temp?.next
        temp?.next = node

        length++
    }

    fun print() {
        var temp = head
        while (temp != null) {
            print(" ${temp.value} ->")
            temp = temp.next
        }
        println("End of list")
    }

    fun reverseLinkedList() {
        println()
        println("Reversing LinkedList")

        tail = head
        var current: Node<T>? = head
        var next: Node<T>? = head
        var prev: Node<T>? = null

        while (current != null) {
            next = current.next
            current.next = prev
            prev = current
            current = next
        }

        head = prev
        // return prev
    }

    fun clear() {
        head = null
        tail = null
        length = 0
    }
}


fun <T : Any> reverseRecursion(node: Node<T>?): Node<T>? {

    //Just in case of empty list.
    if (node == null) {
        return null
    }

    var newHead = node

    if (node.next != null) {
        newHead = reverseRecursion(node.next) //will return next and node will become prev
        //println("NewHead-> ${newHead?.value} , node -> ${node.value}")
        //print(node)
        //print(newHead)
        node.next?.next = node
        // 4-11 = 4.11.next = 11.next = 4  ==> 11 -> 4
        // 3-4  = 3.4.next = 4.next = 3   ==> 11 -> 4 -> 3
        // 2-3  = 2.3.next = 3.next = 2   ==> 11 -> 4 -> 3 -> 2
        // 1-2  = 1.2.next = 2.next = 1   ==> 11 -> 4 -> 3 -> 2 -> 1
    }

    node.next = null
    //print("${newHead?.value},${node?.value} ")
    return newHead
    /*

    To visualize it better, have a stack and then append item 4,3,2,1 on top of stack after 11.
        |11
        |4  4.next? 11, 11.next? = 4 ,
        |3  3.next? 4, 4.next? null 3.next.next = 3
        |2  4.ne
        |1

       turns
       1
       2
       3
       4
       11

     */
}

fun main() {

    val head = Node(1, null)
    val two = Node(2, null)
    val three = Node(3, null)
    val four = Node(4, null)

    val linkedList = LinkedList<Int>()
    linkedList.add(head).add(two).add(three).add(four).add(Node(11, null))
    linkedList.print()

//    linkedList.reverseLinkedList()
//    linkedList.print()
//    print(linkedList)

//    linkedList.reverseLinkedList()
//    linkedList.print()
//    print(linkedList)

//    println()
//    println("Print recursion ")

    val node = reverseRecursion(linkedList.head)
    linkedList.head = node //RESETTING head here.
    //println("Recursively reversed linkedlist")
    println(node)
    linkedList.print()

    linkedList.insert(2, Node(12, null))

    println()
    linkedList.print()
}