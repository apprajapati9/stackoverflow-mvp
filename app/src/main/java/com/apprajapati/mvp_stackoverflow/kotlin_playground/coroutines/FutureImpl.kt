package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlin.random.Random

fun Future.then(left: Future?, f: (result: Any?) -> Future?): Future {
    val then = Then()
    then.left = left
    then.thenFunction = f
    return then
}


interface Future {
    fun poll(data: Any?): Any?
}

val thenFunctionF: (result: Any) -> Future? = { result ->
    Done(result = result)
}

class Then : Future {
    var left: Future? = null
    var right: Future? = null
    var thenFunction = thenFunctionF

    override fun poll(data: Any?): Any? {
        if (left != null) {
            val result = left!!.poll(data)
            if (result != null) {
                right = thenFunction(result)
                left = null
            }
            return null
        } else {

            assert(right != null)
            return right!!.poll(data)
        }
    }

}

class Done(val result: Any? = null) : Future {

    override fun poll(data: Any?): Any? {
        //println("Data -> $result")
        return result
    }

}


class EmitChar() : Future {
    override fun poll(data: Any?): Any? {
        var random = Random.nextInt(0, 26)
        random += 97
        if (random == 97 || random == 98 || random == 101) {
            return random
        } else {
            println("EmitChar -> ${random.toChar()}")
            return null
        }
    }

}

class Counter(var i: Int = 0, val n: Int) : Future {

    override fun poll(data: Any?): Any? {
        if (i < n) {
            println("Counter1 -> $i")
            i++
            return null
        } else {
            //return n
            return "Counter $n"
        }
    }

    fun reset() {
        i = 0
    }

}

fun returnDone(result: Any?): Future? {
    println("Done Type,Value -> ${result!!::class.simpleName}, $result")
    return Done(result)
}

fun main() {
    val futures = mutableListOf<Future>()
    val counter = Counter(n = 5)
    val f1 = Then()
    f1.left = counter
    f1.thenFunction = { result ->
        if (result is Int) {
            Counter(i = result + 5, n = result + 10)
        } else
            returnDone(result)
    }


    val f2 = Then()
    f2.left = Counter(i = 20, n = 25)
    f2.thenFunction = { result ->
        returnDone(result)
    }
    val f3 = Then()
    f3.left = EmitChar()
    f3.thenFunction = { result ->
        returnDone(result)
    }

    futures.add(f1)
    futures.add(f2)
    futures.add(f3)
    var quit = false

    val results = mutableListOf<Any?>()
    while (!quit) {

        quit = true
        results.clear()
        futures.forEach { future ->
            if (future.poll(null) == null) {
                quit = false
            }
        }

//        f1.poll(null)
//        f2.poll(null)
        //println("Polling..")
    }
}