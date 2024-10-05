package com.apprajapati.mvp_stackoverflow.design_patterns.mediator

import org.junit.jupiter.api.Test

/* Mediator design pattern : provide a central object used for communicating between objects
 - example - client server structure where mediator will facilitate the communication between the two

 objects don't talk to each other directly
 It reduces the dependencies between objects

in the code blow, mediator handles the sending and receiving of message for users and handles users.
 */

class ChatUser(
    private val mediator: Mediator,
    private val name: String
    ){

    fun send(message: String){
        println("$name: Sending a message $message")
        mediator.sendMessage(msg = message, user = this)
    }

    fun receive(msg: String) {
        println("Public message: for $name : ${msg}")
    }

    fun privateMessageReceive(message: String, from: ChatUser){
        println("Private message: message from ${from.name} to $name : $message")
    }

    fun sendTo(message: String, to: ChatUser){
        mediator.sendToMessage(message = message, userTo = to, userFrom = this)
    }
}

class Mediator {
    private val users = arrayListOf<ChatUser>()

    fun addUser(user: ChatUser) : Mediator = apply {
            users.add(user)
        }

    fun sendMessage(msg: String, user: ChatUser) {
        users.filter {
            it != user
        }.forEach {
            it.receive(msg)
        }
    }

    fun sendToMessage(message: String, userTo: ChatUser, userFrom: ChatUser) {
        users.firstOrNull {
            it == userTo
        }?.privateMessageReceive(message, userFrom)
    }
}

class MediatorTest {
    @Test
    fun testMediatorPattern(){
        val mediator = Mediator()

        val ajay = ChatUser(mediator, "Ajay")
        val john = ChatUser(mediator, "John")
        val alice = ChatUser(mediator, "Alice")
        val bushra = ChatUser(mediator, "Bushra")

        mediator.addUser(ajay).addUser(john).addUser(alice).addUser(bushra)

        ajay.send("Hi everyone!")
        john.send("hi Ajay")

        ajay.sendTo("Hi Alice", alice)
        alice.sendTo("Hi Ajay, what's up", ajay)

        ajay.sendTo("Hi Elisha", bushra)
    }
}