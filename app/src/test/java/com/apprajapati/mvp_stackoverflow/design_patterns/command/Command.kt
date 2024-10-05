package com.apprajapati.mvp_stackoverflow.design_patterns.command

import org.junit.jupiter.api.Test

/* Command design pattern :  A request wrapped in an object that contains all request info
 then object is passed to the correct handler.

 */

interface Command {
    fun execute()
}

class AddOrderCommand(val id: Long) : Command {
    override fun execute() {
        println("Adding order with id $id")
    }
}

class PayOrderCommand(val id: Long) : Command {
    override fun execute() {
        println("Paying for order with id $id")
    }
}

class CommandProcessor {
    private val queue = arrayListOf<Command>()

    fun addToQueue(command: Command) : CommandProcessor = apply {
        queue.add(command)
    }

    fun processCommands(): CommandProcessor =
        apply {
            queue.forEach {
                it.execute()
            }
            queue.clear()
        }
}

class CommandTest {

    @Test
    fun commandTest(){
        CommandProcessor().addToQueue(
            AddOrderCommand(1L)
        ).addToQueue(
            AddOrderCommand(2L)
        ).addToQueue(
            PayOrderCommand(2L)
        ).addToQueue(
            PayOrderCommand(1L)
        ).processCommands()
    }
}