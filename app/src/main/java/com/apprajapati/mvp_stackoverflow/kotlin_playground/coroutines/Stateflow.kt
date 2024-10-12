package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import androidx.core.app.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

/*
    if you want to collect stateflow for xml views then
    lifecycleScope.launch {
    }
    is not enough. You have to use

    repeatOnLifecycle(Lifecycle.State.Started){
        viewmodel.state.collect {
        }
    }
    above needs to be done in launch to make sure memory isn't leaked and resources aren't wasted.
    by default state flows are hot and they keep updating even in background, thus need to do this.
 */
// generic to do perform the above step on every flow
//fun <T> ComponentActivity.lifecycleCollectFlow(flow : Flow<T>, collect: suspend (T) -> Unit) {
//    CoroutineScope(context = Dispatchers.Default).launch {
//        repeatOnLifecycle(Lifecycle.State.STARTED) {
//            flow.collect(collect)
//        }
//    }

    /*
        if flow<Int> can be said as Flow<T> any type.
        if T is int,
        flow.collect{ number ->
        }
        this will emit T of Int values.. in lambda function.

        So in this one we are passing suspend (T) -> Unit  function
        which will act as { number -> } for flow
     */
//}

//you can use above function in Activity
/*
    lifecycleCollectFlow(viewmodel.stateFlow) { number ->
        binding.textview = number
    }


    SHAREDFLOW : are used for one time event for example, showing snackbar,
    you don't wanna show it again after screen rotation,
        you don't want to show keyboard again..
        all this one time events - for that you should use sharedflow.

 */

fun countDownFlow() = flow<Int> {
    var counter = 10
    emit(counter)
    while(counter > 0) {
        counter--
        delay(500)
        emit(counter)
    }
}



suspend fun collectStateShowcase(){
    /*
    collectLatest - if for some delay if new emission is done
        then last emissions are omitted and block of collect gets cancelled and latest value is emitted.
     */
    countDownFlow().collectLatest {
            number ->
        delay(550) // delayed here and new value is emitted every 500ms and delay here is 550 so omitted value will print last
        //because everytime while waiting, new value is emitted thus cancelling println and emitting new value.
        println("collectLatest -> emitted $number")
    }
}

suspend fun mapFilterOnEach(){
    //self explanatory
    countDownFlow()
        .filter {
            it % 2 == 0
        }
        .map {
            //map are used to transform given data, here its int so transform that into something??
                value -> value * value
            //(value+2) * value //could be anything.

        }
        .onEach {
            println("onEach print -> emitted $it")
        }
        .collect {
            println("filtered -> emitted $it")
        }
}

fun flatMapAndMapShowcase(){
    //flattening list.. combines 2 lists to 1 essentially
    val list1 = (1..5).toList()
    val list2 = (5..10).toList()
    val flattened = listOf(list1, list2).flatMap {
            value ->
        value.toList()
    }
    println( "flattened list  -> $flattened")

    val numbers = listOf(1, 2, 3)

    // Without flatMap (using map)
    val mapped = numbers.map { listOf(it, it * 2) }
    println("map() -> $mapped")  // [[1, 2], [2, 4], [3, 6]]

    // Using flatMap
    val flatMapped = numbers.flatMap { listOf(it, it * 2) }
    println("flatmap() -> $flatMapped")  // [1, 2, 2, 4, 3, 6]
}

suspend fun bufferExample(){
    val flow = flow {
        delay(100)
        emit("Appetizer")

        delay(100)
        emit("main dish")

        delay(100)
        emit("dessert")
    }

    flow.onEach {
        println("delivered $it")
    }.buffer()// When using buffer, it will not wait for collect to finish, it will immediately emit values
        .collect {
            println("eating.... $it")
            delay(1000)
            println("done eating..$it")
            // until collect finishes emit will hold back values from emitting. to discard this, you can use BUFFER()

        }
}

suspend fun conflateExample(){
    val flow = flow {
        delay(100)
        emit("Appetizer")

        delay(100)
        emit("main dish")

        delay(100)
        emit("dessert")
    }

    flow.onEach {
        println("delivered $it")
    }.conflate()
        //this works similarly to collectLatest() but not totally
        // when there are multiple emissions, ie. main dish and dessert are sent together, it will skip the main dish and act on latest emission which is dessert.
        .collect {
            println("eating.... $it")
            delay(1000)
            println("done eating..$it")
            // until collect finishes emit will hold back values from emitting. to discard this, you can use BUFFER()

        }
}


//TODO: need to implement examples of StateFlow
suspend fun main(){

    //buffer to let emissions flow without worrying about collect time constraints.
    //bufferExample()

    //conflate example
    conflateExample()

    //collect collects all emissions, delay in collect makes wait for emissions, emit will wait until collect is done with whole block of code executed.
    countDownFlow().collect {
        number ->
            println("emitted $number")
    }

    //-------- uncomment this to checkout collectLatest ----
    //collectStateShowcase()


    //-------- Uncomment to check mapFilterOnEach =----
    // mapFilterOnEach()

    //similar to filter - interesting thing, this will take full time of emission with delay to get final result.
    val count = countDownFlow().count {
        num ->
            num % 2 == 0 // 2,4,6,8,10 -> total 6 values including 0.. thus 6.
        //it calculates the number of times this condition is satisfied.
    }

    println("count $count")

    //reduce --
    // accumulator - accumulator keeps track of values, if last value of accumulator is 10, then next emission, it is 10
    // values - emitted value..
    val reduce = countDownFlow().reduce {
        accumulator, value -> accumulator + value
    // 10+0, 10+9->19, 19+8=27, 27+7= 34, 34+6=40, 40+5=45, 45+4=49, 49+3=52, 52+2=54, 54+1=55, 55+0 = 55
        //so in a way accumulator accumulates values as its name suggests
    }
    println("reduce -> $reduce")

    //Fold - exactly like reduce, but fold has starting value..
    // so 100+55 = 155 would be the output...
    // fold just provides a starting value to add and then accumulate
    val fold = countDownFlow().fold(100) {
        accumulator , value -> accumulator+value
    }
    println("fold -> $fold")

    //flatMapAndMapShowcase()
}