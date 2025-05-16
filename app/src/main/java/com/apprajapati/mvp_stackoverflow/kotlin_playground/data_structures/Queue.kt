package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures

interface Queue<T : Any> {
    val count: Int
    val isEmpty: Boolean

    fun peak(): T?
    fun enqueue(element: T): Boolean
    fun dequeue(): T?
}

//FIFO - first in, first out
class QueueImpl<T : Any> : Queue<T> {

    val storage = arrayListOf<T>()

    override val count: Int
        get() = storage.size

    override val isEmpty: Boolean
        get() = count == 0

    override fun peak(): T? {
        return storage.getOrNull(0)
    }

    override fun enqueue(element: T): Boolean {
        return storage.add(element)
    }

    override fun dequeue(): T? {
        return if (isEmpty) null else storage.removeAt(0)
    }
}