package com.apprajapati.mvp_stackoverflow.kotlin_playground.coroutines.flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun main() {
    val counter = MutableStateFlow(0)


    //StateFlow has value property which you can access directly without collecting
    println(counter.value)
    //can assign, but not thread safe and could lead to bugs
    counter.value = 2

    //instead use .update{} function

    counter.update { value ->
        value + 1
    }
    println(counter.value)

}

/*
    To convert flow to StateFlow

    .stateIn(
        scope = viewModelScope,
        initialValue = UiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

 */