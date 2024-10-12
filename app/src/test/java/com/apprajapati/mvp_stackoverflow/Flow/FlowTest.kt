package com.apprajapati.mvp_stackoverflow.Flow

import app.cash.turbine.test
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FlowShowCase {

    suspend fun countDownFlow() = flow {
        var counter = 10
        emit(counter)
        while(counter > 0) {
            counter--
            delay(500)
            emit(counter)
        }
    }
}

class FlowTest {

    private var sut : FlowShowCase ?= null
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @get:Rule
//    val dispatcher = UnconfinedTestDispatcher(null, "test")

    @BeforeEach
    fun setup(){

        sut = FlowShowCase()
    }

    //runTest brings time-control capabilities to your test, because of delay() you need withContext() to simulate wait
    @Test
    fun `countDownFlow, properly counts down from 10 to 0`() = runTest{
        withContext(Dispatchers.Default) {
            sut?.countDownFlow()?.test {
                for(i in 10 downTo 1){
                    val emission = awaitItem()
                    Assertions.assertThat(emission).isEqualTo(i)
                }
            }
        }

    }
}