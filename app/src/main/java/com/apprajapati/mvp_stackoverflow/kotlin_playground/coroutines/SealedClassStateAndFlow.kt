package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

sealed class UIState<T>(val data: T?= null, val message: String? = null){

    class Loading<T>(data: T? = null) : UIState<T>(data)
    class Success<T>(data: T): UIState<T>(data)
    class Error<T>(data: T? = null, message: String): UIState<T>(data, message)
}

fun getProducts(product: String, onClick : (String) -> Unit) {
    onClick(product)
}

//success(data)
//error (null, message)
// Loading(null)

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val data: T?= null, val message: String) : NetworkResult<T>()
    class Loading<T: Any>(val data: T? = null): NetworkResult<T>()
}

sealed class Response<T>{
    class Success<T>(val data: T) : Response<T>()
}

fun main() = runBlocking {
    println("main starts")

    joinAll(
        //since the delay is more for 1st one, it will get done after 2nd.
        //so it is to showcase that main thread keeps working and while coroutines are delayed and continued
        async {  coroutine(1, 500) },
        async { coroutine(2, 300) }
    )

    val flow = emitFlow().onEach {
        state ->
            when(state){
                is UIState.Loading ->
                    println("pushed loading..")

                is UIState.Error -> println("pushed Error..${state.message}")
                is UIState.Success -> println("pushed Success..${state.data}")
            }
    }
    flow.collect{

    }

    val network= emitNetwork().onEach {
            state ->
        when(state){
            is NetworkResult.Loading ->
                println("pushed loading..")

            is NetworkResult.Error -> println("pushed Error..${state.message}")
            is NetworkResult.Success -> println("pushed Success..${state.data}")
        }
    }
    network.collect{

    }

    getProducts("return product -> ajay", onClick =  {
        product->
            println(product)
    })

}

suspend fun emitNetwork(): Flow<NetworkResult<String>> = flow {
    emit(NetworkResult.Loading())
    delay(1000)
    emit(NetworkResult.Success("AJAY success"))
    delay(1000)
    emit(NetworkResult.Error(message = "error ajay message"))
}

suspend fun emitFlow(): Flow<UIState<Int>> = flow {
    emit(UIState.Loading())
    delay(1000)
    emit(UIState.Success(1))
    delay(1000)
    emit(UIState.Error(message = "error ajay message"))
}


suspend fun coroutine(number: Int, delay:Long) {
    println("thread is ${Thread.currentThread().name}")
    println("coroutine $number started")
    delay(delay)
    println("coroutine $number has finished.")
}

/*
    state.Success(data: T)

 */





/*
    Coroutines
    Sealed class state
    Write test cases.
    Try the one from MVP youtube video.
    delegation pattern
    listener pattern and practical example --- internet connection this one -- and another example
    operator invoke()
 */