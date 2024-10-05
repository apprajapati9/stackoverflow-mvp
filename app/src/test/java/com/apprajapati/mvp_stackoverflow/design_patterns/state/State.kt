package com.apprajapati.mvp_stackoverflow.design_patterns.state

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/* State : An object changes its behavior based on an internal state
At any moment, there's a finite number of states a program can be in and decide
how to behave based on the current state.

state can be encapsulated in an object.
 */

sealed class AuthorizationState

object Unauthorized : AuthorizationState()

class Authorized(val username: String): AuthorizationState()

class AuthPresenter {
    private var state: AuthorizationState = Unauthorized

    val authorized: Boolean
        get() = when(state){
            is Unauthorized -> false
            is Authorized -> true
        }

    val username: String
        get() {
            return when(val state = this.state){
                is Authorized -> state.username
                is Unauthorized -> "Unknown"
            }
        }

    fun loginUser(username: String){
        state = Authorized(username)
    }

    fun logout(){
        state = Unauthorized
    }

    override fun toString(): String = "User $username is logged in: $authorized"
}


class StateTest {
    @Test
    fun testStatePattern(){
        //action and test
        val presenter = AuthPresenter()
        Assertions.assertThat(presenter.authorized).isFalse()

        //action and test
        presenter.loginUser("Ajay")
        Assertions.assertThat(presenter.authorized).isTrue()
        Assertions.assertThat(presenter.username).isEqualTo("Ajay")
        println(presenter.toString())

        //action and test
        presenter.logout()
        Assertions.assertThat(presenter.authorized).isFalse()
        println(presenter.toString())
    }
}
